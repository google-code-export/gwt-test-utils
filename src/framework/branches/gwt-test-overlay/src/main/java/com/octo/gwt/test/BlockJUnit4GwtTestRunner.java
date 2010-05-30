package com.octo.gwt.test;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class BlockJUnit4GwtTestRunner extends BlockJUnit4ClassRunner {


	public BlockJUnit4GwtTestRunner(Class<?> klass) throws InitializationError, ClassNotFoundException, NotFoundException, CannotCompileException {
		super(createGwtTestClassLoader().loadClass(klass.getName()));
	}
	
	private static ClassLoader createGwtTestClassLoader() throws NotFoundException, CannotCompileException{
		Loader loader= new Loader();
		Thread.currentThread().setContextClassLoader(loader);
		loader.delegateLoadingOf("org.junit.");
		loader.delegateLoadingOf("org.easymock.");	
		
		loader.addTranslator(ClassPool.getDefault(), new GwtTestTranslator());
		return loader;
	}

}
