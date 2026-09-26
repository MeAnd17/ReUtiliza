package com.reutiliza.app.viewmodel;

import com.reutiliza.app.data.repository.ProductRepository;
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
public final class ExploreViewModel_Factory implements Factory<ExploreViewModel> {
  private final Provider<ProductRepository> productRepoProvider;

  public ExploreViewModel_Factory(Provider<ProductRepository> productRepoProvider) {
    this.productRepoProvider = productRepoProvider;
  }

  @Override
  public ExploreViewModel get() {
    return newInstance(productRepoProvider.get());
  }

  public static ExploreViewModel_Factory create(Provider<ProductRepository> productRepoProvider) {
    return new ExploreViewModel_Factory(productRepoProvider);
  }

  public static ExploreViewModel newInstance(ProductRepository productRepo) {
    return new ExploreViewModel(productRepo);
  }
}
