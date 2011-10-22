package com.octo.gwt.test.uibinder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.utils.GwtReflectionUtils;
import com.octo.gwt.test.utils.UiBinderBeanUtils;

/**
 * Handles <g:Xxx /> tags, where Xxx is a {@link IsWidget} implementation.
 * 
 * @author Gael Lazzari
 * 
 * @param <T> The widget type
 */
public class UiBinderWidget<T extends IsWidget> implements UiBinderTag {

  private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("^\\{(\\w+)\\.{1}([^\\}]*)}$");

  private final Map<String, Object> attributesMap;
  private final UiBinderTag parentTag;
  private final T wrapped;

  /**
   * Constructs a new UiBinder tag which wraps a {@link IsWidget} instance.
   * 
   * @param wrapped The wrapped {@link IsWidget} instance
   * @param attributes XML attributes to parse
   * @param parentTag The parent UiBinder tag
   * @param owner The {@link UiBinder} owner widget, which calls the
   *          {@link UiBinder#createAndBindUi(Object)} method to initialize
   *          itself.
   * @param resourceManager The resourceManager associated with the owner.
   */
  public UiBinderWidget(T wrapped, Attributes attributes,
      UiBinderTag parentTag, Object owner, UiResourceManager resourceManager) {
    this.wrapped = wrapped;
    this.parentTag = parentTag;
    this.attributesMap = new HashMap<String, Object>();

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrName = attributes.getLocalName(i);
      String attrValue = attributes.getValue(i).trim();
      String attrUri = attributes.getURI(i);

      if (UiBinderUtils.isUiFieldAttribute(attrUri, attrName)) {
        treatUiFieldAttr(owner, attrValue);
      } else if ("styleName".equals(attrName)) {

        // special case of style
        attributesMap.put("styleName",
            UiBinderUtils.getEffectiveStyleName(attrValue));

      } else if ("addStyleNames".equals(attrName)) {

        // special case of multiple style to add
        treatAddStyleNamesAttr(attrValue);

      } else {

        // normal attribute
        treatStandardAttr(owner, resourceManager, attrName, attrValue);

      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiBinderTag#addElement(com.google.gwt.dom.client
   * .Element)
   */
  public final void addElement(Element element) {
    appendElement(this.wrapped, element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiBinderTag#addWidget(com.google.gwt.user.client
   * .ui.IsWidget)
   */
  public final void addWidget(IsWidget isWidget) {
    addWidget(this.wrapped, isWidget);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderTag#appendText(java.lang.String)
   */
  public final void appendText(String data) {
    if (!"".equals(data.trim())) {
      appendText(this.wrapped, data);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderTag#getParentTag()
   */
  public UiBinderTag getParentTag() {
    return parentTag;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderTag#getWrapped()
   */
  public Object getWrapped() {
    UiBinderBeanUtils.populateWidget(this.wrapped, attributesMap);
    return wrapped;
  }

  protected void addWidget(T wrapped, IsWidget isWidget) {
    if (ForIsWidget.class.isInstance(wrapped)) {
      ((ForIsWidget) wrapped).add(isWidget);
    } else if (HasWidgets.class.isInstance(wrapped)) {
      ((HasWidgets) wrapped).add(isWidget.asWidget());
    }

  }

  protected void appendElement(T wrapped, Element child) {
    wrapped.asWidget().getElement().appendChild(child);
  }

  protected void appendText(T wrapped, String data) {
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Text text = JavaScriptObjects.newText(data);
      wrapped.asWidget().getElement().appendChild(text);
    }
  }

  private void treatAddStyleNamesAttr(String attrValue) {
    String[] styles = attrValue.trim().split(" ");
    String[] effectiveStyles = new String[styles.length];

    for (int j = 0; j < styles.length; j++) {
      effectiveStyles[j] = UiBinderUtils.getEffectiveStyleName(styles[j]);
    }
    attributesMap.put("addStyleNames", effectiveStyles);
  }

  private void treatStandardAttr(Object owner,
      UiResourceManager resourceManager, String attrName, String attrValue) {
    Matcher m = ATTRIBUTE_PATTERN.matcher(attrValue);
    if (m.matches()) {
      String resourceAlias = m.group(1);
      Object resource = resourceManager.getUiResource(resourceAlias);
      if (resource == null) {
        throw new GwtTestUiBinderException("Error in file '"
            + owner.getClass().getSimpleName()
            + ".ui.xml' : no resource declared for alias '" + resourceAlias
            + "'");
      }

      // handle "alias.myCssResource.myClass"
      String[] properties = m.group(2).trim().split("\\.");
      try {
        for (String property : properties) {
          resource = GwtReflectionUtils.callPrivateMethod(resource, property);
        }
      } catch (Exception e) {
        if (GwtTestException.class.isInstance(e)) {
          throw (GwtTestException) e;
        } else {
          throw new GwtTestUiBinderException(
              "Error while calling property '"
                  + m.group(2)
                  + "' on object of type '"
                  + resourceManager.getUiResource(resourceAlias).getClass().getName()
                  + "'", e);
        }
      }
      attributesMap.put(attrName, resource);

    } else {
      attributesMap.put(attrName, attrValue);
    }
  }

  private void treatUiFieldAttr(Object owner, String attrValue) {
    try {
      GwtReflectionUtils.setPrivateFieldValue(owner, attrValue, this.wrapped);
    } catch (ReflectionException e) {
      // ui:field has no corresponding @UiField declared : just ignore it
    }
  }

}
