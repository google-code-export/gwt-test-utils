package com.octo.gwt.test;

import javassist.ClassPool;
import javassist.Loader;

import org.junit.runners.BlockJUnit4ClassRunner;

public class BlockJUnit4GwtTestRunner extends BlockJUnit4ClassRunner {


	public BlockJUnit4GwtTestRunner(Class<?> klass) throws Exception {
		super(createGwtTestClassLoader().loadClass(klass.getName()));
	}
	
	private static ClassLoader createGwtTestClassLoader() throws Exception {
		Loader loader= new Loader();
		Thread.currentThread().setContextClassLoader(loader);
		loader.delegateLoadingOf("org.junit.");
		loader.delegateLoadingOf("org.easymock.");	
		
		loader.addTranslator(ClassPool.getDefault(), new GwtTestTranslator());
		return loader;
	}

}
