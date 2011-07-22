package com.octo.gwt.test.integration.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MyObject implements IsSerializable {

  private final List<MyChildObject> myChildObjects = new ArrayList<MyChildObject>();
  private String myField;
  private transient String myTransientField = "transient field";

  public MyObject(String myField) {
    this.myField = myField;
  }

  /**
   * Default constructor for serialization
   */
  MyObject() {

  }

  public List<MyChildObject> getMyChildObjects() {
    return myChildObjects;
  }

  public String getMyField() {
    return myField;
  }

  public String getMyTransientField() {
    return myTransientField;
  }

  public void setMyField(String myField) {
    this.myField = myField;
  }

  public void setMyTransientField(String myTransientField) {
    this.myTransientField = myTransientField;
  }

}
