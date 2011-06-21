package com.octo.gwt.test.uibinder.specialization;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.user.client.ui.RootPanel;
import com.octo.gwt.test.GwtTestTest;

public class UiBinderWithUiBinderSpecializationsTest extends GwtTestTest {

  @Test
  public void checkUiBinderWithUiBinderSpecializations() {
    // Arrange
    UiBinderWithMoreThanOneUiBinderFactoriesForSameType w = new UiBinderWithMoreThanOneUiBinderFactoriesForSameType();

    // Act
    RootPanel.get().add(w);

    // Assert
    Assert.assertEquals("item created by @UiFactory",
        w.itemWidget.genericLabel.getText());
    Assert.assertEquals("person created by @UiFactory",
        w.personWidget.genericLabel.getText());
  }
}
