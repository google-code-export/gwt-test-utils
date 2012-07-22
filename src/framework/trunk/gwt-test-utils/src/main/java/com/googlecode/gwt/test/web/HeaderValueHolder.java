/*
 * Copyright 2002-2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.googlecode.gwt.test.web;

import static org.fest.assertions.Assertions.assertThat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Internal helper class that serves as value holder for request headers. Adapted from
 * <strong>spring-test</strong>.
 * 
 * @author Juergen Hoeller
 * @author Rick Evans
 * @author Gael Lazzari
 */
class HeaderValueHolder {

   /**
    * Find a HeaderValueHolder by name, ignoring casing.
    * 
    * @param headers the Map of header names to HeaderValueHolders
    * @param name the name of the desired header
    * @return the corresponding HeaderValueHolder, or <code>null</code> if none found
    */
   public static HeaderValueHolder getByName(Map<String, HeaderValueHolder> headers, String name) {
      assertThat(name).as("Header name must not be null").isNotNull();
      for (String headerName : headers.keySet()) {
         if (headerName.equalsIgnoreCase(name)) {
            return headers.get(headerName);
         }
      }
      return null;
   }

   /**
    * Merge the given array into the given Collection.
    * 
    * @param array the array to merge (may be <code>null</code>)
    * @param collection the target Collection to merge the array into
    */
   private static void mergeArrayIntoCollection(Object array, Collection<Object> collection) {
      if (collection == null) {
         throw new IllegalArgumentException("Collection must not be null");
      }
      Object[] arr = toObjectArray(array);
      for (Object elem : arr) {
         collection.add(elem);
      }
   }

   /**
    * Convert the given array (which may be a primitive array) to an object array (if necessary of
    * primitive wrapper objects).
    * <p>
    * A <code>null</code> source value will be converted to an empty Object array.
    * 
    * @param source the (potentially primitive) array
    * @return the corresponding object array (never <code>null</code>)
    * @throws IllegalArgumentException if the parameter is not an array
    */
   private static Object[] toObjectArray(Object source) {
      if (source instanceof Object[]) {
         return (Object[]) source;
      }
      if (source == null) {
         return new Object[0];
      }
      if (!source.getClass().isArray()) {
         throw new IllegalArgumentException("Source is not an array: " + source);
      }
      int length = Array.getLength(source);
      if (length == 0) {
         return new Object[0];
      }
      Class<?> wrapperType = Array.get(source, 0).getClass();
      Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
      for (int i = 0; i < length; i++) {
         newArray[i] = Array.get(source, i);
      }
      return newArray;
   }

   private final List<Object> values = new LinkedList<Object>();

   public void addValue(Object value) {
      this.values.add(value);
   }

   public void addValueArray(Object values) {
      mergeArrayIntoCollection(values, this.values);
   }

   public void addValues(Collection<?> values) {
      this.values.addAll(values);
   }

   public List<String> getStringValues() {
      List<String> stringList = new ArrayList<String>(this.values.size());
      for (Object value : this.values) {
         stringList.add(value.toString());
      }
      return Collections.unmodifiableList(stringList);
   }

   public Object getValue() {
      return (!this.values.isEmpty() ? this.values.get(0) : null);
   }

   public List<Object> getValues() {
      return Collections.unmodifiableList(this.values);
   }

   public void setValue(Object value) {
      this.values.clear();
      this.values.add(value);
   }

}
