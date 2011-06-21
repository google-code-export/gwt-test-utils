package com.octo.gwt.test.internal;

import java.util.HashSet;
import java.util.Set;

import javassist.CtClass;

import com.google.gwt.core.client.JavaScriptObject;
import com.octo.gwt.test.internal.ClassesScanner.ClassVisitor;

/**
 * The class used to build the jsoList.txt file, which contains all
 * {@link JavaScriptObject} classes in third-party jars. <strong>For internal
 * use only.</strong>
 * 
 * <p>
 * TODO : write a maven plugin instead ?
 * </p>
 * 
 * @author Gael Lazzari
 * 
 */
public class JsoScanner {

  public static void main(String[] args) throws Exception {

    final CtClass jsoClass = GwtClassPool.getClass("com.google.gwt.core.client.JavaScriptObject");

    final Set<CtClass> jsoClasses = new HashSet<CtClass>();
    ClassVisitor jsoVisitor = new ClassVisitor() {

      public void visit(CtClass ctClass) {
        if (ctClass.subclassOf(jsoClass)) {
          jsoClasses.add(ctClass);
        }
      }
    };

    Set<String> rootPackages = new HashSet<String>();
    rootPackages.add("com.google.");

    ClassesScanner.getInstance().scanPackages(jsoVisitor, rootPackages);

    for (CtClass ctClass : jsoClasses) {
      System.out.println(ctClass.getName());
    }

    System.out.println("JSO number : " + jsoClasses.size());

  }

}
