package com.reutiliza.app.data.repository;

import com.reutiliza.app.data.local.SessionManager;
import com.reutiliza.app.data.remote.ReUtilizaApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class ProductRepository_Factory implements Factory<ProductRepository> {
  private final Provider<ReUtilizaApiService> apiProvider;

  private final Provider<SessionManager> sessionProvider;

  public ProductRepository_Factory(Provider<ReUtilizaApiService> apiProvider,
      Provider<SessionManager> sessionProvider) {
    this.apiProvider = apiProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public ProductRepository get() {
    return newInstance(apiProvider.get(), sessionProvider.get());
  }

  public static ProductRepository_Factory create(Provider<ReUtilizaApiService> apiProvider,
      Provider<SessionManager> sessionProvider) {
    return new ProductRepository_Factory(apiProvider, sessionProvider);
  }

  public static ProductRepository newInstance(ReUtilizaApiService api, SessionManager session) {
    return new ProductRepository(api, session);
  }
}
