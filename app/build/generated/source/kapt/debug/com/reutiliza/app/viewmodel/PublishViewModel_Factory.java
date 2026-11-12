package com.reutiliza.app.viewmodel;

import com.reutiliza.app.data.repository.AuthRepository;
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
public final class PublishViewModel_Factory implements Factory<PublishViewModel> {
  private final Provider<ProductRepository> productRepoProvider;

  private final Provider<UserRepository> userRepoProvider;

  private final Provider<AuthRepository> authRepoProvider;

  public PublishViewModel_Factory(Provider<ProductRepository> productRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<AuthRepository> authRepoProvider) {
    this.productRepoProvider = productRepoProvider;
    this.userRepoProvider = userRepoProvider;
    this.authRepoProvider = authRepoProvider;
  }

  @Override
  public PublishViewModel get() {
    return newInstance(productRepoProvider.get(), userRepoProvider.get(), authRepoProvider.get());
  }

  public static PublishViewModel_Factory create(Provider<ProductRepository> productRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<AuthRepository> authRepoProvider) {
    return new PublishViewModel_Factory(productRepoProvider, userRepoProvider, authRepoProvider);
  }

  public static PublishViewModel newInstance(ProductRepository productRepo, UserRepository userRepo,
      AuthRepository authRepo) {
    return new PublishViewModel(productRepo, userRepo, authRepo);
  }
}
