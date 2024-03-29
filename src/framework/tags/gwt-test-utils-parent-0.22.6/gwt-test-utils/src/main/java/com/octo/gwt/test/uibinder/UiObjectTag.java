package com.octo.gwt.test.uibinder;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Text;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.octo.gwt.test.exceptions.GwtTestUiBinderException;
import com.octo.gwt.test.exceptions.ReflectionException;
import com.octo.gwt.test.internal.utils.JavaScriptObjects;
import com.octo.gwt.test.internal.utils.JsoProperties;
import com.octo.gwt.test.utils.GwtReflectionUtils;

/**
 * Base handler for any object tag (e.g. &lt;g:Xxx /> tags, where Xxx is either
 * {@link UIObject} subclass or a {@link IsWidget} subtype. This class is
 * expected to be extended to add custom code to handle specific widget /
 * attributes.
 * 
 * @author Gael Lazzari
 * 
 * @param <T> The wrapped object subtype
 * 
 * @see UiObjectTag#instanciate(Class)
 * @see UiObjectTag#initializeObject(IsWidget, Map)
 * @see UiObjectTag#finalizeObject(IsWidget)
 */
public abstract class UiObjectTag<T> implements UiTag<T> {

  private UiTag<?> parentTag;
  private T wrapped;

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiTag#addElement(com.google.gwt.dom.client.Element
   * )
   */
  public final void addElement(Element element) {

    String namespaceURI = JavaScriptObjects.getString(element,
        JsoProperties.XML_NAMESPACE);

    appendElement(this.wrapped, element, namespaceURI,
        UiBinderXmlUtils.getChildWidgets(element));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiTag#addUiObject(com.google.gwt.user.client
   * .ui.UIObject)
   */
  public final void addUiObject(UIObject uiObject) {
    addUIObject(this.wrapped, uiObject);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.octo.gwt.test.uibinder.UiTag#addWidget(com.google.gwt.user.client.ui
   * .Widget)
   */
  public final void addWidget(Widget isWidget) {
    addWidget(this.wrapped, isWidget);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiTag#appendText(java.lang.String)
   */
  public final void appendText(String data) {
    if (!"".equals(data.trim())) {
      appendText(this.wrapped, data);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiTag#endTag()
   */
  public final T endTag() {
    finalizeObject(wrapped);
    return wrapped;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.octo.gwt.test.uibinder.UiTag#getParentTag()
   */
  public final UiTag<?> getParentTag() {
    return parentTag;
  }

  /**
   * Add a new UIObject which is not a {@link IsWidget} instance as a child to
   * this uiObject. This implementation does nothing, the method is expected to
   * be overridden.
   * 
   * @param wrapped The wrapped uiObject of this tag.
   * @param uiObject The UIObject instance to add, which is not a
   *          {@link IsWidget} instance.
   * 
   */
  protected void addUIObject(T wrapped, UIObject uiObject) {

  }

  /**
   * Add a new Widget as a child to this uiObject. This implementation just
   * calls {@link ForWidget#add(Widget)} or
   * {@link HasWidgets#add(com.google.gwt.user.client.ui.Widget)} according to
   * which interface the wrapped uiObject is implementing.
   * 
   * @param wrapped The wrapped uiObject of this tag.
   * @param isWidget The child widget to be added.
   */
  protected void addWidget(T wrapped, Widget isWidget) {
    if (HasWidgets.class.isInstance(wrapped)) {

      // hack for GWT 2.0.3
      if (wrapped instanceof HTMLPanel) {
        HTMLPanel htmlPanel = (HTMLPanel) wrapped;
        GwtReflectionUtils.callPrivateMethod(htmlPanel, "add", isWidget,
            htmlPanel.getElement());
      } else {
        ((HasWidgets) wrapped).add(isWidget);
      }
    }

  }

  /**
   * Append an element declared in the .ui.xml to this uiObject, which is
   * supposed to be its parent. This implementation has one of the following
   * behavior :
   * <ul>
   * <li>If the wrapped {@link UIObject} implements {@link HasHTML}, the child
   * would be appended through
   * {@link Element#appendChild(com.google.gwt.dom.client.Node)}</li>
   * <li>Otherwise, a {@link GwtTestUiBinderException} would be thrown with
   * message: 'Found unexpected child element : <x:xxxx>'</li>
   * </ul>
   * 
   * @param wrapped The wrapped uiObject of this tag.
   * @param element The child element to be appended.
   * @param namespaceURI The namespace URI of the child element.
   * @param childWidgets The element's childs widgets, which could be empty if
   *          no child has been added to it.
   */
  protected void appendElement(T wrapped, Element element, String namespaceURI,
      List<Widget> childWidgets) {

    if (HasHTML.class.isInstance(wrapped)) {
      getElement(wrapped).appendChild(element);
    } else {
      String elementToString = (namespaceURI != null && namespaceURI.length() > 0)
          ? namespaceURI + ":" + element.getTagName() : element.getTagName();
      throw new GwtTestUiBinderException("Found unexpected child element : <"
          + elementToString + "> in " + wrapped.getClass().getName());
    }
  }

  /**
   * Append text to this uiObject. This implementation calls
   * {@link HasText#setText(String)} if the current uiObject is implementing the
   * {@link HasText} interface, or append a new {@link Text} node wrapping the
   * data value to the Widget's element.
   * 
   * @param wrapped The wrapped uiObject of this tag.
   * @param data The string value.
   */
  protected void appendText(T wrapped, String data) {
    if (HasText.class.isInstance(wrapped)) {
      ((HasText) wrapped).setText(data);
    } else {
      Element element = getElement(wrapped);
      Text text = JavaScriptObjects.newText(data, element.getOwnerDocument());
      element.appendChild(text);
    }
  }

  /**
   * A callback executed after every standard uiObject properties have been
   * setup to be able to process any custom finalization on the wrapped
   * uiObject.
   * 
   * @param uiObject The uiObject to finalize.
   */
  protected abstract void finalizeObject(T uiObject);

  protected Element getElement(T wrapped) {
    if (wrapped instanceof UIObject) {
      return ((UIObject) wrapped).getElement();
    } else if (wrapped instanceof Widget) {
      return ((Widget) wrapped).getElement();
    } else {
      throw new GwtTestUiBinderException(
          "Cannot retrieve the Element instance in instances of '"
              + wrapped.getClass().getName() + "', you have to override "
              + this.getClass() + ".getElement(..) protected method");
    }
  }

  /**
   * A callback method executed just after the corresponding UiBinder tag was
   * opened to be able to process any custom initialization on the wrapped
   * uiObject.
   * 
   * @param wrapped The uiObject to initialize
   * @param attributes map of attributes of the wrapped uiObject, with attribute
   *          XML names as keys, corresponding objects as values. This map will
   *          be used to populate the wrapped uiObject just after this callback
   *          would be executed.
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   * @return The created instance
   */
  protected abstract void initializeObject(T wrapped,
      Map<String, Object> attributes, Object owner);

  /**
   * Method responsible for the uiObject instanciation. It is called only if the
   * uiBinder tag is not a provided {@link UiField} and not annotated with
   * either {@link UiFactory} nor {@link UiConstructor}. This implementation
   * simply check for a zero-arg constructor to call and would throw an
   * exception if it does not exist.
   * 
   * @param clazz The uiObject class to instanciate.
   * @param attributes map of attributes of the wrapped uiObject, with attribute
   *          XML names as keys, corresponding objects as values.
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   * @return The created instance.
   */
  protected T instanciate(Class<? extends T> clazz,
      Map<String, Object> attributes, Object owner) {
    try {
      Constructor<? extends T> defaultCons = clazz.getDeclaredConstructor();
      return GwtReflectionUtils.instantiateClass(defaultCons);
    } catch (NoSuchMethodException e) {
      throw new GwtTestUiBinderException(
          clazz.getName()
              + " has no default (zero args) constructor. You have to register a custom "
              + UiObjectTagFactory.class.getSimpleName()
              + " by calling the protected method 'addUiObjectTagFactory' of your test class and override the 'instanciate(Class<T>) method in it");
    }
  }

  /**
   * Callback method called whenever a new uiBinder tag is opened, so
   * implementation could apply some custom initialization.
   * 
   * @param clazz The class of the object to be wrapped in this UiTag.
   * @param namespaceURI The namespace URI of the opened tag
   * @param attributes map of attributes of the wrapped uiObject, with attribute
   *          XML names as keys, corresponding objects as values.
   * @param parentTag The parent tag
   * @param owner The owner of the UiBinder template, with {@link UiField}
   *          fields.
   */
  final void startTag(Class<? extends T> clazz, Map<String, Object> attributes,
      UiTag<?> parentTag, Object owner) {

    this.parentTag = parentTag;

    wrapped = UiBinderInstanciator.getInstance(clazz, attributes, owner);

    if (wrapped == null) {
      wrapped = instanciate(clazz, attributes, owner);
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

    initializeObject(wrapped, attributes, owner);

    UiBinderBeanUtils.populateObject(wrapped, attributes);
  }

}
