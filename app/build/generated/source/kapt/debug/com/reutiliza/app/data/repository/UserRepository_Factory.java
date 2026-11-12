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
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<ReUtilizaApiService> apiProvider;

  private final Provider<SessionManager> sessionProvider;

  public UserRepository_Factory(Provider<ReUtilizaApiService> apiProvider,
      Provider<SessionManager> sessionProvider) {
    this.apiProvider = apiProvider;
    this.sessionProvider = sessionProvider;
  }

  @Override
  public UserRepository get() {
    return newInstance(apiProvider.get(), sessionProvider.get());
  }

  public static UserRepository_Factory create(Provider<ReUtilizaApiService> apiProvider,
      Provider<SessionManager> sessionProvider) {
    return new UserRepository_Factory(apiProvider, sessionProvider);
  }

  public static UserRepository newInstance(ReUtilizaApiService api, SessionManager session) {
    return new UserRepository(api, session);
  }
}
