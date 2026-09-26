package com.reutiliza.app.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.reutiliza.app.data.repository.ChatRepository;
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
public final class ProductDetailViewModel_Factory implements Factory<ProductDetailViewModel> {
  private final Provider<ProductRepository> productRepoProvider;

  private final Provider<UserRepository> userRepoProvider;

  private final Provider<ChatRepository> chatRepoProvider;

  private final Provider<FirebaseAuth> authProvider;

  public ProductDetailViewModel_Factory(Provider<ProductRepository> productRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<ChatRepository> chatRepoProvider,
      Provider<FirebaseAuth> authProvider) {
    this.productRepoProvider = productRepoProvider;
    this.userRepoProvider = userRepoProvider;
    this.chatRepoProvider = chatRepoProvider;
    this.authProvider = authProvider;
  }

  @Override
  public ProductDetailViewModel get() {
    return newInstance(productRepoProvider.get(), userRepoProvider.get(), chatRepoProvider.get(), authProvider.get());
  }

  public static ProductDetailViewModel_Factory create(
      Provider<ProductRepository> productRepoProvider, Provider<UserRepository> userRepoProvider,
      Provider<ChatRepository> chatRepoProvider, Provider<FirebaseAuth> authProvider) {
    return new ProductDetailViewModel_Factory(productRepoProvider, userRepoProvider, chatRepoProvider, authProvider);
  }

  public static ProductDetailViewModel newInstance(ProductRepository productRepo,
      UserRepository userRepo, ChatRepository chatRepo, FirebaseAuth auth) {
    return new ProductDetailViewModel(productRepo, userRepo, chatRepo, auth);
  }
}
