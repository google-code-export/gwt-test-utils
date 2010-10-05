package com.octo.gwt.test.internal.patcher.tools.i18n;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.Messages.DefaultMessage;
import com.google.gwt.i18n.client.Messages.PluralCount;
import com.google.gwt.i18n.client.Messages.PluralText;
import com.google.gwt.i18n.client.PluralRule;
import com.google.gwt.i18n.client.impl.plurals.DefaultRule;

public class MessagesInvocationHandler extends LocalizableResourcesInvocationHandler {

	public MessagesInvocationHandler(Class<? extends LocalizableResource> proxiedClass) {
		super(proxiedClass);
	}

	@Override
	protected Object extractFromProperties(Properties localizedProperties, Method method, Object[] args, Locale locale) throws Throwable {
		PluralText pluralText = method.getAnnotation(PluralText.class);

		String key = (pluralText == null) ? method.getName() : getPluralTextSpecificKey(pluralText, method, args, locale);

		String result = extractProperty(localizedProperties, key);
		if (result != null) {
			return format(result, args, locale);
		}

		return null;

	}

	private String getPluralTextSpecificKey(PluralText pluralText, Method method, Object[] args, Locale locale) {
		String pluralCountValue = extractPluralCountValue(method, args, locale);

		return method.getName() + "[" + pluralCountValue + "]";
	}

	@Override
	protected Object extractDefaultValue(Method method, Object[] args, Locale locale) throws Throwable {
		DefaultMessage defaultMessage = method.getAnnotation(DefaultMessage.class);
		PluralText pluralText = method.getAnnotation(PluralText.class);
		String valuePattern = null;
		if (pluralText != null) {
			valuePattern = extractPluralCountPattern(pluralText, method, args, locale);
		}
		if (valuePattern == null && defaultMessage != null) {
			valuePattern = defaultMessage.value();
		}
		if (valuePattern != null) {
			return format(valuePattern, args, locale);
		}

		return null;
	}

	private String extractPluralCountPattern(PluralText pluralText, Method method, Object[] args, Locale locale) {
		String pluralCountValue = extractPluralCountValue(method, args, locale);
		Map<String, String> pluralForms = getPluralForms(pluralText);

		return pluralForms.get(pluralCountValue);
	}

	@SuppressWarnings("unchecked")
	private String extractPluralCountValue(Method method, Object[] args, Locale locale) {
		Annotation[][] annotations = method.getParameterAnnotations();
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

						return ruleInstance.pluralForms()[ruleInstance.select(count)].getName();

					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Cannot find PluralRule for method '" + method.getDeclaringClass().getSimpleName() + "."
								+ method.getName() + "()'. Expected PluralRule : '" + pluralRuleClassName + "'");
					} catch (InstantiationException e) {
						throw new RuntimeException("Error during instanciation of class '" + pluralRuleClassName + "'");
					} catch (IllegalAccessException e) {
						throw new RuntimeException("Error during instanciation of class '" + pluralRuleClassName + "'");
					}
				}
			}
		}

		throw new RuntimeException("Bad configuration of '" + method.getDeclaringClass() + "." + method.getName()
				+ "' : a @PluralText is declared but no @PluralCount set on any method parameter'");
	}

	private Map<String, String> getPluralForms(PluralText pluralText) {
		Map<String, String> pluralForms = new HashMap<String, String>();

		String[] annotationValue = pluralText.value();
		for (int i = 0; i < annotationValue.length; i++) {
			pluralForms.put(annotationValue[i], annotationValue[++i]);
		}

		return pluralForms;
	}

	private String format(String pattern, Object[] args, Locale locale) {
		return new MessageFormat(pattern, locale).format(args);
	}

}
