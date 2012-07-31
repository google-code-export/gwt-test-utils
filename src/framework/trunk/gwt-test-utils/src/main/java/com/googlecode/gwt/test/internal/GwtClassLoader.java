package com.googlecode.gwt.test.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

import com.google.gwt.dev.javac.CompilationState;
import com.google.gwt.dev.javac.CompiledClass;
import com.google.gwt.dev.util.Name;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.exceptions.GwtTestException;
import com.googlecode.gwt.test.exceptions.GwtTestPatchException;
import com.googlecode.gwt.test.internal.rewrite.OverlayTypesRewriter;

/**
 * <p>
 * gwt-test-utils custom {@link ClassLoader} used to load classes referenced inside a test class
 * extending {@link GwtTest}.<strong>For internal use only : do not refer directly to this
 * classloader in your client code.</strong>
 * </p>
 * <p>
 * It aims to provide JVM-compliant versions of classes referenced in those test classes. To obtain
 * JVM-compliant code, this class loader relies on a set of class {@link Patcher} which can be
 * configured using the <strong>META-INF\gwt-test-utils.properties</strong> file of your
 * application.
 * </p>
 * 
 * <p>
 * In addition to {@link Patcher}, this classloader also perform some bytecode modification on every
 * GWT JavaScriptObject subtype, also known as <strong>Overlay types</strong>.
 * </p>
 * 
 * 
 * @see GwtTranslator
 * @see OverlayTypesRewriter
 * 
 * @author Bertrand Paquet
 * @author Gael Lazzari
 * 
 */
public class GwtClassLoader extends Loader {

   private static class GwtClassLoaderWithRewriter extends GwtClassLoader {

      private final OverlayTypesRewriter overlayRewriter;

      private GwtClassLoaderWithRewriter(ConfigurationLoader configurationLoader,
               CompilationState compilationState, OverlayTypesRewriter overlayRewriter)
               throws NotFoundException, CannotCompileException {
         super(configurationLoader, compilationState);

         this.overlayRewriter = overlayRewriter;
      }

      /**
       * Performs Overlay type support bytecode manipulation
       * 
       * @param className the name of the class to find
       * @param rewriter
       * @return the class byte array, or null if class has not been found
       * @throws NotFoundException
       * @throws CannotCompileException
       * @throws IOException
       */
      @Override
      protected byte[] findClassBytes(String className) throws ClassNotFoundException {

         if (overlayRewriter.isJsoIntf(className)) {
            // Generate a synthetic JSO interface class.
            return overlayRewriter.writeJsoIntf(className);
         }

         // A JSO impl class needs the class bytes for the original class.
         String classFromPool = (overlayRewriter.isJsoImpl(className)) ? className.substring(0,
                  className.length() - 1) : className;

         // Apply Patchers
         byte[] classBytes = super.findClassBytes(classFromPool);

         // perform Overlay types bytecode transformation
         classBytes = overlayRewriter.rewrite(className, classBytes);

         return classBytes;
      }
   }

   static GwtClassLoader createClassLoader(ConfigurationLoader configurationLoader,
            CompilationState compilationState, OverlayTypesRewriter overlayRewriter) {
      try {
         if (overlayRewriter == null) {
            return new GwtClassLoader(configurationLoader, compilationState);
         } else {
            return new GwtClassLoaderWithRewriter(configurationLoader, compilationState,
                     overlayRewriter);
         }
      } catch (Exception e) {
         throw new GwtTestException("Error during " + GwtClassLoader.class.getSimpleName()
                  + " instanciation :", e);
      }
   }

   private final CompilationState compilationState;

   private final Pattern delegatePattern;

   private ProtectionDomain domain;

   private final ClassPool source;

   private final Translator translator;

   private GwtClassLoader(ConfigurationLoader configurationLoader, CompilationState compilationState)
            throws NotFoundException, CannotCompileException {
      super(GwtClassPool.get());
      this.compilationState = compilationState;
      this.source = GwtClassPool.get();
      this.translator = new GwtTranslator(configurationLoader);

      StringBuilder sb = new StringBuilder("^(");
      sb = appendPackageToDelegate(sb, "java.");
      sb = appendPackageToDelegate(sb, "javax.");
      sb = appendPackageToDelegate(sb, "sun.");
      sb = appendPackageToDelegate(sb, "com.sun.");
      sb = appendPackageToDelegate(sb, "org.w3c.");
      sb = appendPackageToDelegate(sb, "org.xml.");

      for (String s : configurationLoader.getDelegates()) {
         if (s.endsWith(".")) {
            sb = appendPackageToDelegate(sb, s);
         } else {
            sb = appendClassToDelegate(sb, s);
         }
      }

      sb.replace(sb.length() - 1, sb.length(), "");
      sb.append(")$");

      delegatePattern = Pattern.compile(sb.toString());
   }

   @Override
   public void setDomain(ProtectionDomain domain) {
      super.setDomain(domain);
      this.domain = domain;
   }

   @Override
   protected Class<?> findClass(String className) throws ClassNotFoundException {
      byte[] classfile = findClassBytes(className);

      int i = className.lastIndexOf('.');
      if (i != -1) {
         String pname = className.substring(0, i);
         if (getPackage(pname) == null)
            try {
               definePackage(pname, null, null, null, null, null, null, null);
            } catch (IllegalArgumentException e) {
               // ignore. maybe the package object for the same
               // name has been created just right away.
            }
      }

      try {
         if (domain == null)
            return defineClass(className, classfile, 0, classfile.length);
         else
            return defineClass(className, classfile, 0, classfile.length, domain);
      } catch (Throwable t) {
         throw new GwtTestPatchException("Error while defining " + className
                  + " from modified bytecode", t);
      }
   }

   protected byte[] findClassBytes(String className) throws ClassNotFoundException {
      try {
         // Apply Patchers
         translator.onLoad(source, className);
         try {
            return source.get(className).toBytecode();
         } catch (NotFoundException e) {
            return null;
         }
      } catch (NotFoundException e) {
         // Try and load the class from the compilation state. The generator
         // will need to have already been run on the class for this to work.
         String internalName = Name.BinaryName.toInternalName(className);
         CompiledClass compiledClass = compilationState.getClassFileMap().get(internalName);

         if (compiledClass != null) {
            addCompiledClass(compiledClass);
            try {
               return applyPatchers(className);
            } catch (Exception e2) {
               throw new ClassNotFoundException(
                        "caught an exception while otaining a class file for generated class "
                                 + className, e2);
            }
         } else {
            throw new ClassNotFoundException(className);
         }
      } catch (Exception e) {
         throw new ClassNotFoundException("caught an exception while obtaining a class file for "
                  + className, e);
      }
   }

   @Override
   protected Class<?> loadClassByDelegation(String name) throws ClassNotFoundException {
      return (delegatePattern.matcher(name).matches()) ? delegateToParent(name) : null;
   }

   private void addCompiledClass(CompiledClass compiledClass) {
      InputStream is = new ByteArrayInputStream(compiledClass.getBytes());

      try {
         source.makeClass(is);
      } catch (Exception e) {
         throw new GwtTestPatchException("Error while handling generated class '"
                  + compiledClass.getInternalName(), e);
      } finally {
         try {
            is.close();
         } catch (IOException e) {
            // don't care
         }
      }
   }

   private StringBuilder appendClassToDelegate(StringBuilder sb, String className) {
      return sb.append(className.replaceAll("\\.", "\\\\\\.").replaceAll("\\$", "\\\\\\$")).append(
               "|");
   }

   private StringBuilder appendPackageToDelegate(StringBuilder sb, String packageName) {
      return sb.append(packageName.replaceAll("\\.", "\\\\\\.")).append(".+|");
   }

   private byte[] applyPatchers(String className) throws NotFoundException, CannotCompileException,
            IOException {
      // Apply Patchers
      translator.onLoad(source, className);
      try {
         return source.get(className).toBytecode();
      } catch (NotFoundException e) {
         return null;
      }
   }

}
