package com.reutiliza.app.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.reutiliza.app.data.repository.ProductRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ProductRepository> productRepoProvider;

  private final Provider<UserRepository> userRepoProvider;

  private final Provider<FirebaseAuth> authProvider;

  public HomeViewModel_Factory(Provider<ProductRepository> productRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<FirebaseAuth> authProvider) {
    this.productRepoProvider = productRepoProvider;
    this.userRepoProvider = userRepoProvider;
    this.authProvider = authProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(productRepoProvider.get(), userRepoProvider.get(), authProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<ProductRepository> productRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<FirebaseAuth> authProvider) {
    return new HomeViewModel_Factory(productRepoProvider, userRepoProvider, authProvider);
  }

  public static HomeViewModel newInstance(ProductRepository productRepo, UserRepository userRepo,
      FirebaseAuth auth) {
    return new HomeViewModel(productRepo, userRepo, auth);
  }
}
