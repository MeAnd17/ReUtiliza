package com.reutiliza.app.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
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

  private final Provider<FirebaseAuth> authProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> chatRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<FirebaseAuth> authProvider) {
    this.chatRepoProvider = chatRepoProvider;
    this.userRepoProvider = userRepoProvider;
    this.authProvider = authProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepoProvider.get(), userRepoProvider.get(), authProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepoProvider,
      Provider<UserRepository> userRepoProvider, Provider<FirebaseAuth> authProvider) {
    return new ChatViewModel_Factory(chatRepoProvider, userRepoProvider, authProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepo, UserRepository userRepo,
      FirebaseAuth auth) {
    return new ChatViewModel(chatRepo, userRepo, auth);
  }
}
