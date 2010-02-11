package com.octo.gwt.test17.internal;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.google.gwt.i18n.client.impl.CurrencyData;
import com.octo.gwt.test17.PatchUtils;

public class PatchCurrencyList {

	private static List<CurrencyData> currencyDatas;
	private static List<String[]> contents;

	private static CurrencyData create() {
		try {
			Constructor<CurrencyData> constructor = CurrencyData.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void init() {
		if (contents != null) {
			return;
		}
		try {
			Properties currencyData = PatchUtils.getLocalizedProperties("com/google/gwt/i18n/client/impl/cldr/CurrencyData");
			Properties currencyExtra = PatchUtils.getProperties("com/google/gwt/i18n/client/constants/CurrencyExtra");
			Properties numberConstants = PatchUtils.getLocalizedProperties("com/google/gwt/i18n/client/constants/NumberConstantsImpl");
			Set<Object> keySet = currencyData.keySet();
			String[] currencies = new String[keySet.size()];
			keySet.toArray(currencies);
			Arrays.sort(currencies);
			Map<String, String> nameMap = new HashMap<String, String>();

			String defCurrencyCode = numberConstants.getProperty("defCurrencyCode");
			if (defCurrencyCode == null) {
				defCurrencyCode = "USD";
			}

			String[] defCurrencyObject = new String[] { defCurrencyCode, defCurrencyCode, Integer.toString(2), "" };
			for (String currencyCode : currencies) {
				String currencyEntry = currencyData.getProperty(currencyCode);
				String[] currencySplit = currencyEntry.split("\\|");
				String currencyDisplay = currencySplit[0];
				String currencySymbol = null;
				if (currencySplit.length > 1 && currencySplit[1].length() > 0) {
					currencySymbol = currencySplit[1];
				}
				int currencyFractionDigits = 2;
				if (currencySplit.length > 2 && currencySplit[2].length() > 0) {
					try {
						currencyFractionDigits = Integer.valueOf(currencySplit[2]);
					} catch (NumberFormatException e) {
					}
				}
				boolean currencyObsolete = false;
				if (currencySplit.length > 3 && currencySplit[3].length() > 0) {
					try {
						currencyObsolete = Integer.valueOf(currencySplit[3]) != 0;
					} catch (NumberFormatException e) {
						// Ignore bad values
					}
				}
				int currencyFlags = currencyFractionDigits;
				String extraData = currencyExtra.getProperty(currencyCode);
				String portableSymbol = "";
				if (extraData != null) {
					// CurrencyExtra contains up to 3 fields separated by |
					//   0 - portable currency symbol
					//   1 - space-separated flags regarding currency symbol positioning/spacing
					//   2 - override of CLDR-derived currency symbol
					String[] extraSplit = extraData.split("\\|");
					portableSymbol = extraSplit[0];
					if (extraSplit.length > 1) {
						if (extraSplit[1].contains("SymPrefix")) {
							currencyFlags |= CurrencyData.POS_FIXED_FLAG;
						} else if (extraSplit[1].contains("SymSuffix")) {
							currencyFlags |= CurrencyData.POS_FIXED_FLAG | CurrencyData.POS_SUFFIX_FLAG;
						}
						if (extraSplit[1].contains("ForceSpace")) {
							currencyFlags |= CurrencyData.SPACING_FIXED_FLAG | CurrencyData.SPACE_FORCED_FLAG;
						} else if (extraSplit[1].contains("ForceNoSpace")) {
							currencyFlags |= CurrencyData.SPACING_FIXED_FLAG;
						}
					}
					// If a non-empty override is supplied, use it for the currency symbol.
					if (extraSplit.length > 2 && extraSplit[2].length() > 0) {
						currencySymbol = extraSplit[2];
					}
					// If we don't have a currency symbol yet, use the portable symbol if supplied.
					if (currencySymbol == null && portableSymbol.length() > 0) {
						currencySymbol = portableSymbol;
					}
				}
				// If all else fails, use the currency code as the symbol.
				if (currencySymbol == null) {
					currencySymbol = currencyCode;
				}
				String[] currencyObject = new String[] { currencyCode, currencySymbol, Integer.toString(currencyFlags), "" };
				if (portableSymbol.length() > 0) {
					currencyObject[3] += portableSymbol;
				}
				if (!currencyObsolete) {
					nameMap.put(currencyCode, currencyDisplay);
					//writer.println("// " + currencyDisplay);
					//writer.println("\":" + quote(currencyCode) + "\": " + currencyObject + ",");
				}
				if (currencyCode.equals(defCurrencyCode)) {
					defCurrencyObject = currencyObject;
				}
			}
			currencyDatas = new ArrayList<CurrencyData>();
			contents = new ArrayList<String[]>();

			currencyDatas.add(create());
			contents.add(defCurrencyObject);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static CurrencyData getDefault() {
		init();
		return currencyDatas.get(0);
	}

	private static String[] get(CurrencyData currencyData) {
		init();
		for (int i = 0; i < currencyDatas.size(); i++) {
			if (currencyDatas.get(i) == currencyData) {
				return contents.get(i);
			}
		}
		return null;
	}

	public static String getCurrencyCode(CurrencyData currencyData) {
		return get(currencyData)[0];
	}

	public static String getCurrencySymbol(CurrencyData currencyData) {
		return get(currencyData)[1];
	}

	public static int getFlagsAndPrecision(CurrencyData currencyData) {
		return Integer.parseInt(get(currencyData)[2]);
	}

	public static void reset() {
		contents = null;
		currencyDatas = null;
	}
}
