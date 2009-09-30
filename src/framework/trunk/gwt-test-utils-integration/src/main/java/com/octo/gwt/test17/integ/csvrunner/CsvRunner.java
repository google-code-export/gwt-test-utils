package com.octo.gwt.test17.integ.csvrunner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class CsvRunner {

	private static final Logger logger = Logger.getLogger(CsvRunner.class);

	private int lineNumber = -1;

	private String extendedLineInfo = null;

	public String getAssertionErrorMessagePrefix() {
		return "Error line " + (lineNumber + 1) + (extendedLineInfo == null ? "" : " [" + extendedLineInfo + "]") + ": ";
	}

	public String getProcessingMessagePrefix() {
		return "Processing line " + (lineNumber + 1) + (extendedLineInfo == null ? "" : " [" + extendedLineInfo + "]") + ": ";
	}

	public int runSheet(List<List<String>> sheet, Object fixture) throws Exception {
		Assert.assertNotNull("Fixture have to be not null", fixture);
		boolean execute = false;
		int lineExecuted = 0;
		lineNumber = 0;
		for (List<String> row : sheet) {
			if (execute) {
				executeRow(row, fixture);
				lineExecuted++;
			}
			if (row != null && row.size() > 0 && "start".equals(row.get(0))) {
				execute = true;
			}
			lineNumber++;
		}
		lineNumber = -1;
		return lineExecuted;
	}

	public void executeRow(List<String> row, Object fixture) throws Exception {
		if (row.size() == 0) {
			return;
		}
		String methodName = row.get(0);
		if (!"".equals(methodName)) {
			List<String> args = new ArrayList<String>();
			args.addAll(row);
			args.remove(0);
			executeLine(methodName, args, fixture);
		}
	}

	private void removeEmptyElements(List<String> list) {
		List<String> newList = new ArrayList<String>();
		for(String s : list) {
			if (!"".equals(s)) {
				newList.add(s);
			}
		}
		list.clear();
		list.addAll(newList);
	}
	
	private void transformArgs(List<String> list) {
		List<String> newList = new ArrayList<String>();
		for(String s : list) {
			String out = s;
			if ("*empty*".equals(s)) {
				out = "";
			} else if ("*null*".equals(s)) {
				out = null;
			}
			newList.add(out);
		}
		list.clear();
		list.addAll(newList);
	}
	
	public void executeLine(String methodName, List<String> args, Object fixture) throws Exception {
		if (methodName.indexOf("**") != 0) {
			List<String> filterArgs = new ArrayList<String>(args);
			removeEmptyElements(filterArgs);
			transformArgs(filterArgs);
			Method m = getMethod(fixture, fixture.getClass(), methodName);
			if (m == null) {
				Assert.fail(getAssertionErrorMessagePrefix() + "Method ' " + methodName + " ' not found in object " + fixture);
			}
			logger.debug(getProcessingMessagePrefix() + "Executing " + methodName + ", params " + Arrays.toString(filterArgs.toArray()));
			List<Object> argList = new ArrayList<Object>();
			for (Class<?> clazz : m.getParameterTypes()) {
				if (filterArgs.size() == 0) {
					Assert.fail(getAssertionErrorMessagePrefix() + "Too few args for " + methodName);
				}
				if (clazz.isArray()) {
					argList.add(filterArgs.toArray(new String[]{}));
					filterArgs.clear();
				} else {
					argList.add(filterArgs.get(0));
					filterArgs.remove(0);
				}
			}
			if (filterArgs.size() != 0) {
				Assert.fail(getAssertionErrorMessagePrefix() + "Too many args for " + methodName);
			}
			try {
				Object [] finalArgList = argList.toArray(new Object[]{});
				m.invoke(fixture, finalArgList);
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof Exception) {
					Exception ex = (Exception) e.getCause();
					throw ex;
				}
				if (e.getCause() instanceof Error) {
					Error er = (Error) e.getCause();
					throw er;
				}
				logger.error(getAssertionErrorMessagePrefix() + "Execution error", e);
				Assert.fail(getAssertionErrorMessagePrefix() + "Error invoking " + methodName + " in fixture " + e.toString());
			} catch (Exception e) {
				logger.error(getAssertionErrorMessagePrefix() + "Execution error", e);
				Assert.fail(getAssertionErrorMessagePrefix() + "Error invoking " + methodName + " in fixture " + e.toString());
			}
		} else {
			logger.debug(getProcessingMessagePrefix() + "commented line : " + methodName + " : " + args);
		}
	}

	private Method getMethod(Object fixture, Class<?> clazz, String methodName) {
		for (Method m : clazz.getDeclaredMethods()) {
			if (methodName.equalsIgnoreCase(m.getName())) {
				m.setAccessible(true);
				return m;
			}
		}
		for (Method m : clazz.getMethods()) {
			if (methodName.equalsIgnoreCase(m.getName())) {
				m.setAccessible(true);
				return m;
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			return getMethod(fixture, superClazz, methodName);
		}
		return null;
	}

	private Field getField(Object fixture, Class<?> clazz, String fieldName) {
		for (Field f : clazz.getDeclaredFields()) {
			if (f.getName().equalsIgnoreCase(fieldName)) {
				f.setAccessible(true);
				return f;
			}
		}
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			return getField(fixture, superClazz, fieldName);
		}
		return null;
	}

	public Object getValue(Object o, Node node) {
		Object current = getValue(true, o, node);
		return current;
	}

	@SuppressWarnings("unchecked")
	public Object getValue(boolean failOnError, Object o, Node node) {
		logger.debug(getProcessingMessagePrefix() + "GetValue on " + o.getClass().getCanonicalName() + ", objectLocalization " + node);
		Object current = o;
		Node currentNode = node;
		while (currentNode != null) {
			if (failOnError) {
				Assert.assertNotNull(getAssertionErrorMessagePrefix() + "Unable to process leaving param list " + node, current);
			} else {
				if (current == null) {
					return null;
				}
			}
			String currentName = currentNode.getLabel();
			boolean mapEqIsProcessed = false;
			logger.trace(getProcessingMessagePrefix() + "Processing " + currentName);
			boolean ok = false;
			if (!ok) {
				Method m = null;
				if (m == null) {
					m = getMethod(current, current.getClass(), currentName);
				}
				if (m == null) {
					m = getMethod(current, current.getClass(), "get" + currentName);
				}
				if (m != null) {
					try {
						if (m.getParameterTypes().length == 0 || currentNode.getParams() != null) {
							current = invoke(current, m, currentNode.getParams());
							ok = true;
						} else {
							current = findInList(current, m, currentNode.getMapEq(), currentNode.getMap());
							mapEqIsProcessed = true;
							ok = true;
						}

					} catch (Exception e) {
						logger.error(getAssertionErrorMessagePrefix() + "Execution error", e);
						Assert.fail(getAssertionErrorMessagePrefix() + "Unable to get method result on " + o.getClass().getCanonicalName()
								+ ", method " + m.getName() + ", params " + currentNode.getParams());
					}
				}
			}
			if (!ok) {
				Field f = getField(current, current.getClass(), currentName);
				if (f != null) {
					try {
						current = f.get(current);
						ok = true;
					} catch (Exception e) {
						logger.error(getAssertionErrorMessagePrefix() + "Execution error", e);
						Assert.fail(getAssertionErrorMessagePrefix() + "Unable to get field value on " + o.getClass().getCanonicalName() + ", field "
								+ f.getName() + ", params " + node);
					}
				}
			}
			if (ok && currentNode.getMap() != null) {
				if (currentNode.getMapEq() == null) {
					current = proccessMap(current, currentNode.getMap());
				} else {
					if (!mapEqIsProcessed) {
						if (current instanceof Iterable<?>) {
							Iterable<Object> list = (Iterable<Object>) current;
							current = findInIterable(list, currentNode.getMapEq(), currentNode.getMap(), current, null);
						} else {
							Assert.fail(getAssertionErrorMessagePrefix() + "Not managed type for iteration " + current.getClass().getCanonicalName());
						}
					}
				}
			}
			if (!ok) {
				if (failOnError) {
					Assert.fail(getAssertionErrorMessagePrefix() + "Not found <" + currentName + "> in " + current.getClass().getCanonicalName());
				} else {
					return null;
				}
			}
			currentNode = currentNode.getNext();
		}
		if (failOnError) {
			Assert.assertNotNull(getAssertionErrorMessagePrefix() + "Object not found or null object " + o.getClass().getCanonicalName()
					+ ", params " + node, current);
		}
		return current;
	}

	private Object invoke(Object current, Method m, List<String> list) throws IllegalArgumentException, IllegalAccessException {
		logger.debug("Invoking " + m.getName() + " on " + current.getClass().getCanonicalName() + " with param " + list);
		m.setAccessible(true);
		if (list == null) {
			if (m.getParameterTypes().length == 0) {
				try {
					return m.invoke(current);
				} catch (InvocationTargetException e) {
					return null;
				}
			}
		}
		Object[] tab = new Object[m.getParameterTypes().length];
		for (int index = 0; index < m.getParameterTypes().length; index++) {
			if (m.getParameterTypes()[index] == String.class) {
				tab[index] = list.get(index);
			} else if (m.getParameterTypes()[index] == Integer.class || m.getParameterTypes()[index] == int.class) {
				tab[index] = Integer.parseInt(list.get(index));
			} else {
				Assert.fail(getAssertionErrorMessagePrefix() + "Not managed type " + m.getParameterTypes()[index]);
			}
		}
		try {
			return m.invoke(current, tab);
		} catch (InvocationTargetException e) {
			return null;
		}
	}

	private Object findInList(final Object current, final Method m, Node mapEq, String map) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (m.getParameterTypes().length != 1 && m.getParameterTypes()[0] != int.class) {
			Assert.fail("Unable to navigate " + current.getClass().getCanonicalName() + " with method " + m.getName());
		}
		Method countM = getMethod(current, current.getClass(), m.getName() + "Count");
		if (countM == null) {
			Assert.fail("Count method not found in " + current.getClass().getCanonicalName() + " method " + m.getName());
		}
		if (countM.getParameterTypes().length > 0) {
			Assert.fail("Too many parameter in count method " + current.getClass().getCanonicalName() + " method " + countM.getName());
		}

		logger.debug("Searching in list, field " + mapEq + ", value " + map);
		final int count = (Integer) countM.invoke(current);
		return findInIterable(new Iterable<Object>() {

			public Iterator<Object> iterator() {
				return new Iterator<Object>() {

					int counter = 0;

					public boolean hasNext() {
						return counter < count;
					}

					public Object next() {
						try {
							return m.invoke(current, counter++);
						} catch (Exception e) {
							throw new RuntimeException("Iterator exception", e);
						}
					}

					public void remove() {
						throw new UnsupportedOperationException("Remove not implemented");
					}

				};
			}

		}, mapEq, map, current, m);
	}

	private Object findInIterable(Iterable<Object> list, Node before, String after, Object current, Method m) {
		Object found = null;
		for (Object n : list) {
			if (checkCondition(n, before, after)) {
				if (found != null) {
					Assert.fail("Not unique object with condition " + before + "=" + after);
				}
				found = n;
			}
		}
		Assert.assertNotNull(getAssertionErrorMessagePrefix() + "Not found " + before + "=" + after + " in " + current.getClass().getCanonicalName()
				+ (m != null ? " method " + m.getName() : ""), found);
		return found;
	}

	private boolean checkCondition(Object n, Node before, String after) {
		Object result = getValue(false, n, before);
		String s = result == null ? null : result.toString();
		return after.equals(s);
	}

	private Object proccessMap(Object current, String map) {
		if (current instanceof Map<?, ?>) {
			Map<?, ?> m = (Map<?, ?>) current;
			current = m.get(map);
		} else if (current instanceof List<?>) {
			List<?> l = (List<?>) current;
			current = l.get(Integer.parseInt(map));
		} else {
			Assert.fail(getAssertionErrorMessagePrefix() + "Object not a map " + current.getClass().getCanonicalName() + " : " + map);
		}
		return current;
	}

	public void setExtendedLineInfo(String extendedLineInfo) {
		this.extendedLineInfo = extendedLineInfo;
	}

}
