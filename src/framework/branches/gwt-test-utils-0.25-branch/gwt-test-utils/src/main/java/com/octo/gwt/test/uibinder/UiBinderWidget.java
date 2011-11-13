package com.octo.gwt.test.uibinder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.GwtTestException;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Base handler for Widgets tag (e.g. <g:Xxx /> tags, where Xxx is a
 * {@link IsWidget} implementation). This class should be overriden to add
 * custom code to handle specific widget / attributes.
 * 
 * @author Gael Lazzari
 * 
 * @param <T> The widget type
 */
public class UiBinderWidget<T extends IsWidget> implements UiBinderTag {

  private static final Pattern EXTERNAL_RESOURCES_PATTERN = Pattern.compile("\\s*\\{(\\S*)\\}\\s*");

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

      if (UiBinderXmlUtils.isUiFieldAttribute(attrUri, attrName)) {
        treatUiFieldAttr(owner, attrValue);
      } else if ("styleName".equals(attrName)) {
        // special case of style
        attributesMap.put("styleName",
            UiBinderXmlUtils.getEffectiveStyleName(attrValue));

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

    String namespaceURI = JavaScriptObjects.getString(element,
        JsoProperties.XML_NAMESPACE);
    List<IsWidget> childWidgets = JavaScriptObjects.getObject(element,
        JsoProperties.UIBINDER_CHILD_WIDGETS_LIST);

    if (childWidgets == null) {
      childWidgets = Collections.emptyList();
    }

    appendElement(this.wrapped, element, namespaceURI, childWidgets);
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
   * @see com.octo.gwt.test.uibinder.UiBinderTag#getWrapped()
   */
  public final Object endTag() {
    UiBinderBeanUtils.populateWidget(wrapped, attributesMap);
    finalizeWidget(wrapped);
    return wrapped;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderTag#getParentTag()
   */
  public UiBinderTag getParentTag() {
    return parentTag;
  }

  /**
   * Add a new Widget as a child to this widget. This implementation just calls
   * {@link ForIsWidget#add(IsWidget)} or
   * {@link HasWidgets#add(com.google.gwt.user.client.ui.Widget)} according to
   * which interface the wrapped widget is implementing.
   * 
   * @param wrapped The wrapped widget of this tag.
   * @param isWidget The child widget to be added.
   */
  protected void addWidget(T wrapped, IsWidget isWidget) {
    if (ForIsWidget.class.isInstance(wrapped)) {
      // hack for GWT 2.1.0
      if (wrapped instanceof HTMLPanel) {
        HTMLPanel htmlPanel = (HTMLPanel) wrapped;
        GwtReflectionUtils.callPrivateMethod(htmlPanel, "add",
            isWidget.asWidget(), htmlPanel.getElement());
      } else {
        ((ForIsWidget) wrapped).add(isWidget);
      }
    } else if (HasWidgets.class.isInstance(wrapped)) {
      ((HasWidgets) wrapped).add(isWidget.asWidget());
    }

  }

  /**
   * Append an element declared in the .ui.xml to this widget, which is supposed
   * to be its parent. This implementation calls
   * {@link Element#appendChild(com.google.gwt.dom.client.Node)} on the Widget's
   * element.
   * 
   * @param wrapped The wrapped widget of this tag.
   * @param element The child element to be appended.
   * @param namespaceURI The namespace URI of the child element.
   * @param childWidgets The element's childs widgets, which could be empty if
   *          no child has been added to it.
   */
  protected void appendElement(T wrapped, Element element, String namespaceURI,
      List<IsWidget> childWidgets) {
    wrapped.asWidget().getElement().appendChild(element);
  }

  /**
   * Append text to this widget. This implementation calls
   * {@link HasText#setText(String)} if the current widget is implementing the
   * {@link HasText} interface, or append a new {@link Text} node wrapping the
   * data value to the Widget's element.
   * 
   * @param wrapped The wrapped widget of this tag.
   * @param data The string value.
   */
  protected void appendText(T wrapped, String data) {
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Text text = JavaScriptObjects.newText(data,
          wrapped.asWidget().getElement().getOwnerDocument());
      wrapped.asWidget().getElement().appendChild(text);
    }
  }

  /**
   * A callback executed after every standard widget properties have been setup
   * to eventually add custom properties to the widget. This implementation does
   * nothing.
   * 
   * @param widget The widget to finalize.
   */
  protected void finalizeWidget(T widget) {

  }

  private Object extractResource(String group,
      UiResourceManager resourceManager, Object owner) {
    String[] splitted = group.split("\\.");
    String resourceAlias = splitted[0];
    Object resource = resourceManager.getUiResource(resourceAlias);
    if (resource == null) {
      throw new GwtTestUiBinderException("Error in file '"
          + owner.getClass().getSimpleName()
          + ".ui.xml' : no resource declared for alias '" + resourceAlias + "'");
    }

    if (splitted.length == 1) {
      return resource;
    }

    // handle "alias.myCssResource.myClass"
    try {
      for (int i = 1; i < splitted.length; i++) {
        resource = GwtReflectionUtils.callPrivateMethod(resource, splitted[i]);
      }

      return resource;

    } catch (Exception e) {
      if (GwtTestException.class.isInstance(e)) {
        throw (GwtTestException) e;
      } else {
        throw new GwtTestUiBinderException("Error while calling property '"
            + group.substring(group.indexOf('.') + 1) + "' on object of type '"
            + resourceManager.getUiResource(resourceAlias).getClass().getName()
            + "'", e);
      }
    }
  }

  private String formatResourcesMessage(String attrValue, List<Object> resources) {

    StringBuilder sb = new StringBuilder();
    Matcher m = EXTERNAL_RESOURCES_PATTERN.matcher(attrValue);

    int foundNb = 0;
    int token = 0;
    while (m.find()) {
      sb.append(attrValue.substring(token, m.start(1))).append(foundNb++);
      token = m.end(1);
    }
    sb.append(attrValue.substring(token));

    return MessageFormat.format(sb.toString(), resources.toArray());
  }

  private void treatAddStyleNamesAttr(String attrValue) {
    String[] styles = attrValue.trim().split(" ");
    String[] effectiveStyles = new String[styles.length];

    for (int j = 0; j < styles.length; j++) {
      effectiveStyles[j] = UiBinderXmlUtils.getEffectiveStyleName(styles[j]);
    }
    attributesMap.put("addStyleNames", effectiveStyles);
  }

  private void treatStandardAttr(Object owner,
      UiResourceManager resourceManager, String attrName, String attrValue) {

    Matcher m = EXTERNAL_RESOURCES_PATTERN.matcher(attrValue);
    if (m.matches()) {
      // entire value matches : only one {} resource, not only string
      attributesMap.put(attrName,
          extractResource(m.group(1), resourceManager, owner));
    } else {
      // we have to find potential {} resources
      m = EXTERNAL_RESOURCES_PATTERN.matcher(attrValue);

      List<Object> resources = new ArrayList<Object>();
      while (m.find()) {
        resources.add(extractResource(m.group(1), resourceManager, owner));
      }

      if (resources.size() == 0) {
        // attribute should be a String or a simple object convertible to a
        // String
        attributesMap.put(attrName, attrValue);
      } else {
        // case '{OBJ1} {Obj2}' : this must be formatted to string
        attributesMap.put(attrName,
            formatResourcesMessage(attrValue, resources));
      }
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
