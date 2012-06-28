/*
 * Copyright 2002-2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.googlecode.gwt.test.web;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Mock implementation of the {@link javax.servlet.ServletConfig} interface.
 * Adapted from <strong>spring-test</strong>
 * 
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Gael Lazzari
 */
public class MockServletConfig implements ServletConfig {

  private final Map<String, String> initParameters = new LinkedHashMap<String, String>();

  private final ServletContext servletContext;

  private final String servletName;

  /**
   * Create a new MockServletConfig with a default {@link MockServletContext}.
   */
  public MockServletConfig() {
    this(null, "");
  }

  /**
   * Create a new MockServletConfig.
   * 
   * @param servletContext the ServletContext that the servlet runs in
   */
  public MockServletConfig(ServletContext servletContext) {
    this(servletContext, "");
  }

  /**
   * Create a new MockServletConfig.
   * 
   * @param servletContext the ServletContext that the servlet runs in
   * @param servletName the name of the servlet
   */
  public MockServletConfig(ServletContext servletContext, String servletName) {
    this.servletContext = (servletContext != null ? servletContext
        : new MockServletContext());
    this.servletName = servletName;
  }

  /**
   * Create a new MockServletConfig with a default {@link MockServletContext}.
   * 
   * @param servletName the name of the servlet
   */
  public MockServletConfig(String servletName) {
    this(null, servletName);
  }

  public void addInitParameter(String name, String value) {
    assertThat(name).as("Parameter name must not be null").isNotNull();
    this.initParameters.put(name, value);
  }

  public String getInitParameter(String name) {
    assertThat(name).as("Parameter name must not be null").isNotNull();
    return this.initParameters.get(name);
  }

  public Enumeration<String> getInitParameterNames() {
    return Collections.enumeration(this.initParameters.keySet());
  }

  public ServletContext getServletContext() {
    return this.servletContext;
  }

  public String getServletName() {
    return this.servletName;
  }

}
