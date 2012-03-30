package com.googlecode.gwt.test.gin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import com.googlecode.gwt.test.server.RemoteServiceCreateHandler;

public class GInjectorCreateHandlerTest extends GwtTestTest {

  @GinModules({M2.class})
  static interface G2 extends Ginjector {
    Virtual v();
  }
  @GinModules({M3.class})
  static interface G3 extends Ginjector {
    VirtualMore more();

    SomeServiceAsync service();

    Virtual v();
  }

  @GinModules(M4.class)
  interface G4 extends Ginjector {
    Virtual v();
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

  static class ImplMore2 implements VirtualMore {
    Virtual core;

    @Inject
    public ImplMore2(Virtual core) {
      super();
      this.core = core;
    }

  }

  // This module will only contain a single binding
  static final class M2 extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(Impl.class).in(Singleton.class);
    }
  }

  static final class M3 extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(Impl2.class);
      bind(VirtualMore.class).to(ImplMore2.class);
    }
  }
  static class M4 extends AbstractGinModule {
    @Override
    protected void configure() {
      bind(Virtual.class).to(ImplementationWithProviders.class);
      bind(VirtualMore.class).to(ImplMore2.class);
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
    G2 g2 = GWT.create(G2.class);

    // Act
    Virtual v = g2.v();

    // Assert
    assertEquals(Impl.class, v.getClass());
  }

  @Test
  public void shouldFallbackToGwtCreate() {
    // Arrange
    G3 g3 = GWT.create(G3.class);

    // Act
    Virtual v = g3.v();
    SomeServiceAsync service = g3.service();

    // Assert
    assertEquals(Impl2.class, v.getClass());
    assertNotNull(service);

    Assert.assertNotNull(g3);
    Assert.assertNotNull(g3.v());
  }

  @Test
  public void shouldInstantiateComplexObjectGraphs() {
    // Arrange
    G3 g3 = GWT.create(G3.class);

    // Act
    VirtualMore more = g3.more();

    // Assert
    assertTrue(more instanceof ImplMore2);
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
    final G4 injector = GWT.create(G4.class);
    final Virtual virtual = injector.v();
  }

}