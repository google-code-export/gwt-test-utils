package com.googlecode.gwt.test.json;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.google.gwt.core.client.JsonUtils;
import com.googlecode.gwt.test.GwtTestTest;

public class JsonUtilsTest extends GwtTestTest {

   @Test
   public void safeEval() {
      // Test
      MyJsonOverlay jso = JsonUtils.safeEval("{myString: 'json string', \"myDouble\": 3, myFloat: 3.1415, \"myBool\": true, 'myStringArray': [\"John Locke\", \"Ben Linus\"], myNumberArray: null, myObject: {myString: 'child object string', myNumberArray: [2.2, 3.3] } }");

      // Assert
      assertThat(jso.getMyString()).isEqualTo("json string");
      assertThat(jso.getMyDouble()).isEqualTo(3);
      assertThat(jso.getMyFloat()).isEqualTo(3.1415f);
      assertThat(jso.getMyBool()).isTrue();
      assertThat(jso.getMyStringArray().get(0)).isEqualTo("John Locke");
      assertThat(jso.getMyStringArray().get(1)).isEqualTo("Ben Linus");
      assertThat(jso.getMyNumberArray()).isNull();

      MyJsonOverlay child = jso.getMyObject();
      assertThat(child.getMyString()).isEqualTo("child object string");
      assertThat(child.getMyNumberArray().get(0)).isEqualTo(2.2);
      assertThat(child.getMyNumberArray().get(1)).isEqualTo(3.3);
   }

}
