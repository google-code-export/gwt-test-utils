package com.googlecode.gwt.test.gin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.googlecode.gwt.test.GwtTestTest;
import com.googlecode.gwt.test.rpc.RemoteServiceCreateHandler;

public class GInjectorCreateHandlerTest extends GwtTestTest {

  @GinModules({Gin1Module.class})
  static interface Gin1Injector extends Ginjector {
    Virtual virtual();
  }

  // This module will only contain a single binding
  static final class Gin1Module extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(Impl.class).in(Singleton.class);
    }
  }

  @GinModules({Gin2Module.class})
  static interface Gin2Injector extends Ginjector {
    SomeServiceAsync service();

    Virtual virtual();

    VirtualMore virtualMore();
  }

  static final class Gin2Module extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(Impl2.class);
      bind(VirtualMore.class).to(ImplMore.class);
    }
  }

  @GinModules({Gin3Module.class})
  static interface Gin3Injector extends Ginjector {
    ImplMore implMore();
  }

  static final class Gin3Module extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(Impl2.class);
    }
  }

  @GinModules(Gin4Module.class)
  interface Gin4Injector extends Ginjector {
    Virtual virtual();
  }

  static class Gin4Module extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(ImplementationWithProviders.class);
      bind(VirtualMore.class).to(ImplMore.class);
    }
  }

  static class Impl implements Virtual {
  }

  static class Impl2 implements Virtual {
    TestMessages messages;

    @Inject
    public Impl2(TestMessages messages) {
      this.messages = messages;
    }
  }

  static class ImplementationWithProviders implements Virtual {
    @Inject
    public ImplementationWithProviders(Provider<VirtualMore> provider) {
    }
  }

  static class ImplMore implements VirtualMore {
    Virtual core;

    @Inject
    public ImplMore(Virtual core) {
      super();
      this.core = core;
    }

  }

  // These bindings test the ability of Ginjector GwtCreateHandler
  // to fallback to GWT.create for unbound ones (like RemoteService,
  // Constants, Messages, etc).
  @RemoteServiceRelativePath("service")
  static interface Service extends RemoteService {
    String name();
  }

  static interface ServiceAsync {
    void name(AsyncCallback<String> callback);
  }

  static class ServiceImpl implements Service {

    public String name() {
      return "Service Implementation";
    }

  }

  @RemoteServiceRelativePath("someService")
  static interface SomeService extends RemoteService {
  }

  static interface SomeServiceAsync {
  }

  static class SomeServiceImpl implements SomeService {

  }

  static interface TestMessages extends Messages {
    @DefaultMessage("this is junit")
    String myName();
  }

  // Simple bindings
  static interface Virtual {
  }
  static interface VirtualMore {
  }

  @Before
  public void beforeGinjectorCreateHandler() {
    addGwtCreateHandler(new GInjectorCreateHandler());
    addGwtCreateHandler(new RemoteServiceCreateHandler() {

      @Override
      protected Object findService(Class<?> remoteServiceClass,
          String remoteServiceRelativePath) {

        if (Service.class.equals(remoteServiceClass)) {
          return new ServiceImpl();
        } else if (SomeService.class.equals(remoteServiceClass)) {
          return new SomeServiceImpl();
        }

        return null;
      }
    });
  }

  @Test
  public void shouldBindAndServe() {
    // Arrange
    Gin1Injector injector1 = GWT.create(Gin1Injector.class);

    // Act
    Virtual v = injector1.virtual();

    // Assert
    assertEquals(Impl.class, v.getClass());
    assertSame(v, injector1.virtual());
  }

  @Test
  public void shouldFallbackToGwtCreate() {
    // Arrange
    Gin2Injector injector2 = GWT.create(Gin2Injector.class);

    // Act
    Virtual virtual = injector2.virtual();
    SomeServiceAsync service = injector2.service();

    // Assert
    assertEquals(Impl2.class, virtual.getClass());
    Assert.assertNotSame(virtual, injector2.virtual());
    assertNotNull(service);
  }

  @Test
  public void shouldInstantiateComplexObjectGraphs() {
    // Arrange
    Gin2Injector injector2 = GWT.create(Gin2Injector.class);

    // Act
    VirtualMore more = injector2.virtualMore();

    // Assert
    assertEquals(ImplMore.class, more.getClass());
  }

  @Test
  public void shouldInstantiateConcreteComplexObjectGraphs() {
    // Arrange
    Gin3Injector injector3 = GWT.create(Gin3Injector.class);

    // Act
    ImplMore more = injector3.implMore();

    // Assert
    assertEquals(Impl2.class, more.core.getClass());
    assertNotNull(((Impl2) more.core).messages);
  }

  /**
   * This is the use case that needs to hold. <code><pre>
   * class Animal {
   * 
   * @Inject Animal (Provider<Sound> soundProvider) { } } </pre></code>
   */
  @SuppressWarnings("unused")
  @Test
  public void shouldInstantiateObjectGraphsContainingProviders() {
    final Gin4Injector injector4 = GWT.create(Gin4Injector.class);
    final Virtual virtual = injector4.virtual();
  }

}