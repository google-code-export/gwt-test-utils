package com.octo.gwt.test.uibinder;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.patchers.dom.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Base handler for Widgets tag (e.g. <g:Xxx /> tags, where Xxx is a
 * {@link IsWidget} implementation). This class is expected to be extended to
 * add custom code to handle specific widget / attributes.
 * 
 * @author Gael Lazzari
 * 
 * @param <T> The widget type
 * 
 * @see UiWidgetTag#instanciate(Class)
 * @see UiWidgetTag#initializeWidget(IsWidget, Map)
 * @see UiWidgetTag#finalizeWidget(IsWidget)
 */
public abstract class UiWidgetTag<T extends IsWidget> implements UiTag {

  private UiTag parentTag;
  private T wrapped;

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
   * @see com.octo.gwt.test.uibinder.UiBinderTag#endTag()
   */
  public final Object endTag() {
    finalizeWidget(wrapped);
    return wrapped;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiBinderTag#getParentTag()
   */
  public final UiTag getParentTag() {
    return parentTag;
  }

  /**
   * Callback method called whenever a new uiBinder tag is opened, so
   * implementation could apply some custom initialization.
   * 
   * @param widgetClass
   * @param namespaceURI The namespace URI of the opened tag
   * @param attributes map of attributes of the wrapped widget, with attribute
   *          XML names as keys, corresponding objects as values.
   * @param parentTag The parent tag
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   */
  final void startTag(Class<? extends T> widgetClass,
      Map<String, Object> attributes, UiTag parentTag, Object owner) {

    this.parentTag = parentTag;

    wrapped = UiBinderInstanciator.getInstance(widgetClass, attributes, owner);

    if (wrapped == null) {
      wrapped = instanciate(widgetClass, attributes, owner);
    }

    String uiFieldValue = (String) attributes.get("ui:field");

    if (uiFieldValue != null) {
      attributes.remove("ui:field");
      try {
        GwtReflectionUtils.setPrivateFieldValue(owner, uiFieldValue, wrapped);
      } catch (ReflectionException e) {
        // ui:field has no corresponding @UiField declared : just ignore it
      }
    }

    initializeWidget(wrapped, attributes, owner);

    UiBinderBeanUtils.populateWidget(wrapped, attributes);
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
   * to be able to process any custom finalization on the wrapped widget.
   * 
   * @param widget The widget to finalize.
   */
  protected abstract void finalizeWidget(T widget);

  /**
   * A callback method executed just after the corresponding UiBinder tag was
   * opened to be able to process any custom initialization on the wrapped
   * widget.
   * 
   * @param wrapped The widget to initialize
   * @param attributes map of attributes of the wrapped widget, with attribute
   *          XML names as keys, corresponding objects as values. This map will
   *          be used to populate the wrapped widget just after this callback
   *          would be executed.
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   * @return The created instance
   */
  protected abstract void initializeWidget(T wrapped,
      Map<String, Object> attributes, Object owner);

  /**
   * Method responsible for the widget instanciation. It is called only if the
   * uiBinder tag is not a provided {@link UiField} and not annotated with
   * either {@link UiFactory} nor {@link UiConstructor}. This implementation
   * simply check for a zero-arg constructor to call and would throw an
   * exception if it does not exist.
   * 
   * @param widgetClass The widget class to instanciate.
   * @param attributes map of attributes of the wrapped widget, with attribute
   *          XML names as keys, corresponding objects as values.
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   * @return The created instance.
   */
  protected T instanciate(Class<? extends T> widgetClass,
      Map<String, Object> attributes, Object owner) {
    try {
      Constructor<? extends T> defaultCons = widgetClass.getDeclaredConstructor();
      return GwtReflectionUtils.instantiateClass(defaultCons);
    } catch (NoSuchMethodException e) {
      throw new GwtTestUiBinderException(
          widgetClass.getName()
              + " has no default (zero args) constructor. You have to register a custom "
              + UiWidgetTagFactory.class.getSimpleName()
              + " by calling the protected method 'addUiBinderWidgetFactory' of your test class and override the 'instanciate(Class<T>) method in it");
    }
  }

}
