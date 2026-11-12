package com.reutiliza.app.viewmodel;

import com.reutiliza.app.data.repository.AuthRepository;
import com.reutiliza.app.data.repository.ChatRepository;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ChatRepository> chatRepoProvider;

  private final Provider<UserRepository> userRepoProvider;

  private final Provider<AuthRepository> authRepoProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> chatRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<AuthRepository> authRepoProvider) {
    this.chatRepoProvider = chatRepoProvider;
    this.userRepoProvider = userRepoProvider;
    this.authRepoProvider = authRepoProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepoProvider.get(), userRepoProvider.get(), authRepoProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<AuthRepository> authRepoProvider) {
    return new ChatViewModel_Factory(chatRepoProvider, userRepoProvider, authRepoProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepo, UserRepository userRepo,
      AuthRepository authRepo) {
    return new ChatViewModel(chatRepo, userRepo, authRepo);
  }
}
