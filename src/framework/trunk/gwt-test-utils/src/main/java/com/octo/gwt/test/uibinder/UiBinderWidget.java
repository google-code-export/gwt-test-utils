package com.octo.gwt.test.uibinder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
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
  public UiBinderWidget(T wrapped, Map<String, Object> attributesMap,
      UiBinderTag parentTag, Object owner, UiResourceManager resourceManager) {
    this.wrapped = wrapped;
    this.parentTag = parentTag;
    this.attributesMap = attributesMap;

    String uiFieldValue = (String) attributesMap.get("ui:field");

    if (uiFieldValue != null) {
      attributesMap.remove("ui:field");
      try {
        GwtReflectionUtils.setPrivateFieldValue(owner, uiFieldValue,
            this.wrapped);
      } catch (ReflectionException e) {
        // ui:field has no corresponding @UiField declared : just ignore it
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
      ((ForIsWidget) wrapped).add(isWidget);
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

}
