package com.octo.gwt.test.demo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.octo.gwt.test.demo.beans.FooBean;

public interface MyServiceAsync {

  void createBean(String name, AsyncCallback<FooBean> callback);

}
