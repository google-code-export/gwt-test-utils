package com.googlecode.gwt.test.internal.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Double-Map implementation. <strong>For internal use only.</strong>
 * 
 * @author Bertrand Paquet
 * 
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class DoubleMap<A, B, C> {

   private final Map<A, Map<B, C>> map;

   public DoubleMap() {
      map = new HashMap<A, Map<B, C>>();
   }

   public C get(A a, B b) {
      Map<B, C> m = map.get(a);
      return m == null ? null : m.get(b);
   }

   public void put(A a, B b, C c) {
      if (map.get(a) == null) {
         map.put(a, new HashMap<B, C>());
      }
      map.get(a).put(b, c);
   }
}
