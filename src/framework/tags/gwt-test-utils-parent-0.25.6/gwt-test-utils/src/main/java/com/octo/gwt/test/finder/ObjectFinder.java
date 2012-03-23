package com.octo.gwt.test.finder;

public interface ObjectFinder {

  boolean accept(String... params);

  Object find(String... params);

}
