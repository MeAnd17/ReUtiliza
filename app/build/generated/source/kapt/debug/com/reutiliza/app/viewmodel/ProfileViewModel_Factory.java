package com.reutiliza.app.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.reutiliza.app.data.repository.AuthRepository;
import com.reutiliza.app.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<UserRepository> userRepoProvider;

  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<FirebaseAuth> authProvider;

  public ProfileViewModel_Factory(Provider<UserRepository> userRepoProvider,
      Provider<AuthRepository> authRepoProvider, Provider<FirebaseAuth> authProvider) {
    this.userRepoProvider = userRepoProvider;
    this.authRepoProvider = authRepoProvider;
    this.authProvider = authProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(userRepoProvider.get(), authRepoProvider.get(), authProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<UserRepository> userRepoProvider,
      Provider<AuthRepository> authRepoProvider, Provider<FirebaseAuth> authProvider) {
    return new ProfileViewModel_Factory(userRepoProvider, authRepoProvider, authProvider);
  }

  public static ProfileViewModel newInstance(UserRepository userRepo, AuthRepository authRepo,
      FirebaseAuth auth) {
    return new ProfileViewModel(userRepo, authRepo, auth);
  }
}
