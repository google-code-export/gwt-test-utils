package com.octo.gwt.test.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DeserializationContext extends HashMap<Class<?>, ISerializeCallback> {

	private static final long serialVersionUID = -2332437262166773402L;
	
	private Map<Class<?>, ISerializeCallback> cache = new HashMap<Class<?>, ISerializeCallback>();
	
	private static final ISerializeCallback defaultSerializeCallback = new ISerializeCallback() {
		
		public Object callback(Object object) throws Exception {
			return object;
		}
		
	};
	
	public Object resolve(Object object) throws Exception {
		Class<?> targetClazz = object.getClass();
		ISerializeCallback callback = cache.get(targetClazz);
		if (callback == null) {
			for (Entry<Class<?>, ISerializeCallback> entry : entrySet()) {
				if (entry.getKey().isAssignableFrom(targetClazz)) {
					callback = entry.getValue();
				}
			}
			if (callback == null) {
				callback = defaultSerializeCallback;
			}
			cache.put(targetClazz, callback);
		}
		return callback.callback(object);
	}

}
