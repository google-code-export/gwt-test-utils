package com.octo.gwt.test.internal.overrides;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;

public class OverrideNodeList<T extends Node> extends NodeList<T> {

  private List<T> list;

  public OverrideNodeList() {
    list = new ArrayList<T>();
  }

  public OverrideNodeList(List<T> list) {
    this.list = list;
  }

  public OverrideNodeList(Set<T> set) {
    this.list = new ArrayList<T>(set);
  }

  public List<T> getList() {
    return list;
  }

}
