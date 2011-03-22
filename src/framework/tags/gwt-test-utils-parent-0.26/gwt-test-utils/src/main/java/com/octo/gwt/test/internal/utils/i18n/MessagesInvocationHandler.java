package com.octo.gwt.test.internal.utils.i18n;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.Messages.AlternateMessage;
import com.google.gwt.i18n.client.Messages.DefaultMessage;
import com.google.gwt.i18n.client.Messages.PluralCount;
import com.google.gwt.i18n.client.Messages.PluralText;
import com.google.gwt.i18n.client.Messages.Select;
import com.google.gwt.i18n.client.PluralRule;
import com.google.gwt.i18n.client.impl.plurals.DefaultRule;

@SuppressWarnings("deprecation")
public class MessagesInvocationHandler extends LocalizableResourcesInvocationHandler {

	public MessagesInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		super(proxiedClass);
	}

	@Override
	protected Object extractFromProperties(Properties localizedProperties, Method method, Object[] args, Locale locale) throws Throwable {
		Annotation messageAnnotation = getMessageAnnotation(method);

		String key = (messageAnnotation == null) ? method.getName() : getSpecificKey(messageAnnotation, method, args, locale);

		String result = extractProperty(localizedProperties, key);
		if (result != null) {
			return format(result, args, locale);
		}

		return null;
	}

	/**
	 * Return an instance of {@link AlternateMessage} or {@link PluralText} if
	 * the i18n method is annotated, null otherwise.
	 * 
	 * @param method
	 *            The current processed i18n method
	 * @return an instance of {@link AlternateMessage} or {@link PluralText} if
	 *         the i18n method is annotated, null otherwise.
	 */
	private Annotation getMessageAnnotation(Method method) {
		Annotation specificMessageAnnotation = method.getAnnotation(AlternateMessage.class);
		if (specificMessageAnnotation == null) {
			specificMessageAnnotation = method.getAnnotation(PluralText.class);
		}
		return specificMessageAnnotation;
	}

	private String getSpecificKey(Annotation specificMessageAnnotation, Method method, Object[] args, Locale locale) {
		String pluralCountValue = extractPluralCountAndSelectValues(specificMessageAnnotation, method, args, locale);

		return method.getName() + "[" + pluralCountValue + "]";
	}

	@Override
	protected Object extractDefaultValue(Method method, Object[] args, Locale locale) throws Throwable {
		DefaultMessage defaultMessage = method.getAnnotation(DefaultMessage.class);
		Annotation messageAnnotation = getMessageAnnotation(method);
		String valuePattern = null;
		if (messageAnnotation != null) {
			String key = extractPluralCountAndSelectValues(messageAnnotation, method, args, locale);
			String[] values = getMessageAnnotationValues(messageAnnotation);
			valuePattern = getAnnotationValues(values).get(key);
		}
		if (valuePattern == null && defaultMessage != null) {
			valuePattern = defaultMessage.value();
		}
		if (valuePattern != null) {
			return format(valuePattern, args, locale);
		}

		return null;
	}

	private String[] getMessageAnnotationValues(Annotation messageAnnotation) {
		if (AlternateMessage.class.isInstance(messageAnnotation)) {
			return ((AlternateMessage) messageAnnotation).value();
		} else if (PluralText.class.isInstance(messageAnnotation)) {
			return ((PluralText) messageAnnotation).value();
		}

		return null;
	}

	/**
	 * Get the {@link PluralCount} and/or {@link Select} value which correspond
	 * to the method call. if there are many @PluralCount and/or @Select
	 * annotated args, the corresponding values are appended with the '|'
	 * separator.
	 * 
	 * @param messageAnnotation
	 *            The annotation which has been detected. Can be a
	 *            {@link AlternateMessage} or a {@link PluralText}
	 * @param method
	 *            The i18n called method
	 * @param args
	 *            The parameter passed to the i18n method during the call
	 * @param locale
	 * @return A String in which are appended all the {@link PluralCount} and
	 *         {@link Select} value, or null if there is no such annotations
	 */
	@SuppressWarnings("unchecked")
	private String extractPluralCountAndSelectValues(Annotation messageAnnotation, Method method, Object[] args, Locale locale) {
		Annotation[][] annotations = method.getParameterAnnotations();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < annotations.length; i++) {
			Annotation[] childArray = annotations[i];
			for (int j = 0; j < childArray.length; j++) {
				if (PluralCount.class.isAssignableFrom(childArray[j].getClass())) {
					PluralCount pluralCount = (PluralCount) childArray[j];
					Class<? extends PluralRule> pluralRuleClass = pluralCount.value();
					int count = (Integer) args[i];

					String pluralRuleClassName = (pluralRuleClass != PluralRule.class) ? pluralRuleClass.getName() : DefaultRule.class.getName();
					pluralRuleClassName += ("_" + locale.getLanguage());

					try {
						Class<? extends PluralRule> acutalRule = (Class<? extends PluralRule>) Class.forName(pluralRuleClassName);
						PluralRule ruleInstance = acutalRule.newInstance();

						sb.append(ruleInstance.pluralForms()[ruleInstance.select(count)].getName()).append("|");

					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Cannot find PluralRule for method '" + method.getDeclaringClass().getSimpleName() + "."
								+ method.getName() + "()'. Expected PluralRule : '" + pluralRuleClassName + "'");
					} catch (InstantiationException e) {
						throw new RuntimeException("Error during instanciation of class '" + pluralRuleClassName + "'");
					} catch (IllegalAccessException e) {
						throw new RuntimeException("Error during instanciation of class '" + pluralRuleClassName + "'");
					}
				} else if (Select.class.isAssignableFrom(childArray[j].getClass())) {
					sb.append(args[i]).append("|");
				}
			}
		}

		if (sb.length() == 0) {
			throw new RuntimeException("Bad configuration of '" + method.getDeclaringClass() + "." + method.getName() + "' : a @"
					+ messageAnnotation.getClass().getSimpleName() + " is declared but no @" + PluralCount.class.getSimpleName() + " or @"
					+ Select.class.getSimpleName() + " set on any method parameter'");
		} else {
			return sb.substring(0, sb.length() - 1);
		}

	}

	/**
	 * Convert the {@link AlternateMessage#value()} or
	 * {@link PluralText#value()} string array to a map of possible values
	 * 
	 * @param annotationValues
	 *            the array value returned by {@link AlternateMessage#value()}
	 *            or {@link PluralText#value()}
	 * @return a map of named values
	 */
	private Map<String, String> getAnnotationValues(String[] annotationValues) {
		Map<String, String> pluralForms = new HashMap<String, String>();

		for (int i = 0; i < annotationValues.length; i++) {
			pluralForms.put(annotationValues[i], annotationValues[++i]);
		}

		return pluralForms;
	}

	private String format(String pattern, Object[] args, Locale locale) {
		return new MessageFormat(pattern, locale).format(args);
	}

}
