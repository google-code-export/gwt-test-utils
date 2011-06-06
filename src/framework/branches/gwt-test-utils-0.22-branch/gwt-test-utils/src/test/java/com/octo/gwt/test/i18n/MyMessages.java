package com.octo.gwt.test.i18n;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;

@DefaultLocale("en")
public interface MyMessages extends Messages {

  String a_message(String name, int number, boolean bool);

  @Key("1234")
  @DefaultMessage("This is a plain string.")
  String oneTwoThreeFour();

  @DefaultMessage("You have {0} widgets")
  @PluralText({"one", "You have {0} widget"})
  String widgetCount(@PluralCount int count);

  @DefaultMessage("No reference to the argument")
  String optionalArg(@Optional String ignored);

  @DefaultMessage("Your cart total is {0,number,currency}")
  @Description("The total value of the items in the shopping cart in local currency")
  String totalAmount(@Example("$5.00") double amount);

  @Meaning("the color")
  @DefaultMessage("orange")
  String orangeColor();

  @Meaning("the fruit")
  @DefaultMessage("orange")
  String orangeFruit();
}
