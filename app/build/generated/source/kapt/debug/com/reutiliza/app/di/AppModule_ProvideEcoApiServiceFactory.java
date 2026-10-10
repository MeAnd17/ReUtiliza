package com.reutiliza.app.di;

import com.reutiliza.app.data.remote.EcoApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideEcoApiServiceFactory implements Factory<EcoApiService> {
  @Override
  public EcoApiService get() {
    return provideEcoApiService();
  }

  public static AppModule_ProvideEcoApiServiceFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static EcoApiService provideEcoApiService() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEcoApiService());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideEcoApiServiceFactory INSTANCE = new AppModule_ProvideEcoApiServiceFactory();
  }
}
