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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<ReUtilizaApiService> apiProvider;

  private final Provider<SessionManager> sessionProvider;

  public AuthRepository_Factory(Provider<ReUtilizaApiService> apiProvider,
      Provider<SessionManager> sessionProvider) {
    this.apiProvider = apiProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiProvider.get(), sessionProvider.get());
  }

  public static AuthRepository_Factory create(Provider<ReUtilizaApiService> apiProvider,
      Provider<SessionManager> sessionProvider) {
    return new AuthRepository_Factory(apiProvider, sessionProvider);
  }

  public static AuthRepository newInstance(ReUtilizaApiService api, SessionManager session) {
    return new AuthRepository(api, session);
  }
}
