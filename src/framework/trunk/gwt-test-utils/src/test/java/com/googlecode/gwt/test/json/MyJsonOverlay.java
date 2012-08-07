package com.googlecode.gwt.test.json;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;

public class MyJsonOverlay extends JavaScriptObject {

   protected MyJsonOverlay() {
   }

   public final native boolean getMyBool() /*-{ return this.myBool; }-*/;

   public final native double getMyDouble() /*-{ return this.myDouble; }-*/;

   public final native float getMyFloat() /*-{ return this.myFloat; }-*/;

   public final native JsArrayNumber getMyNumberArray() /*-{ return this.myNumberArray; }-*/;

   public final native MyJsonOverlay getMyObject() /*-{ return this.myObject; }-*/;

   public final native String getMyString() /*-{ return this.myString; }-*/;

   public final native JsArrayString getMyStringArray() /*-{ return this.myStringArray; }-*/;

}
