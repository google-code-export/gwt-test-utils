package com.octo.gwt.test17.ng;

public interface SubClassedObject {

	PropertyContainer getOverrideProperties();
	
	class Helper {

		public static void setProperty(Object o, String propertyName, Object propertyValue) {
			if (o instanceof SubClassedObject) {
				SubClassedObject subClassedObject = (SubClassedObject) o;
				subClassedObject.getOverrideProperties().put(propertyName, propertyValue);
			}
			else {
				throw new RuntimeException("Bad object for setProperty " + o.getClass());
			}
		}
		
		@SuppressWarnings("unchecked")
		public static <T> T getProperty(Object o, String propertyName) {
			if (o instanceof SubClassedObject) {
				SubClassedObject subClassedObject = (SubClassedObject) o;
				return (T) subClassedObject.getOverrideProperties().get(propertyName);
			}
			else {
				throw new RuntimeException("Bad object for getProperty " + o.getClass());
			}
		}
		
	}
}
