package com.reutiliza.app.viewmodel;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.reutiliza.app.data.model.Product;
import com.reutiliza.app.data.model.ProductCategory;
import com.reutiliza.app.data.model.ProductCondition;
import com.reutiliza.app.data.model.TransactionType;
import com.reutiliza.app.data.repository.ProductRepository;
import com.reutiliza.app.data.repository.UserRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u001aJ\u000e\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*J\u000e\u0010+\u001a\u00020&2\u0006\u0010\'\u001a\u00020\u001aJ\u0006\u0010,\u001a\u00020&R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000fR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u000fR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u000fR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/reutiliza/app/viewmodel/PublishViewModel;", "Landroidx/lifecycle/ViewModel;", "productRepo", "Lcom/reutiliza/app/data/repository/ProductRepository;", "userRepo", "Lcom/reutiliza/app/data/repository/UserRepository;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "(Lcom/reutiliza/app/data/repository/ProductRepository;Lcom/reutiliza/app/data/repository/UserRepository;Lcom/google/firebase/auth/FirebaseAuth;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/reutiliza/app/viewmodel/PublishUiState;", "description", "", "getDescription", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "price", "getPrice", "selectedCategory", "Lcom/reutiliza/app/data/model/ProductCategory;", "getSelectedCategory", "selectedCondition", "Lcom/reutiliza/app/data/model/ProductCondition;", "getSelectedCondition", "selectedImages", "", "Landroid/net/Uri;", "getSelectedImages", "selectedTransactionType", "Lcom/reutiliza/app/data/model/TransactionType;", "getSelectedTransactionType", "title", "getTitle", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addImage", "", "uri", "publish", "context", "Landroid/content/Context;", "removeImage", "resetState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PublishViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.ProductRepository productRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.UserRepository userRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.viewmodel.PublishUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.reutiliza.app.viewmodel.PublishUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> title = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> description = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> price = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.model.ProductCategory> selectedCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.model.TransactionType> selectedTransactionType = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.model.ProductCondition> selectedCondition = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<android.net.Uri>> selectedImages = null;
    
    @javax.inject.Inject()
    public PublishViewModel(@org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.ProductRepository productRepo, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.UserRepository userRepo, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth auth) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.reutiliza.app.viewmodel.PublishUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.model.ProductCategory> getSelectedCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.model.TransactionType> getSelectedTransactionType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.model.ProductCondition> getSelectedCondition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<android.net.Uri>> getSelectedImages() {
        return null;
    }
    
    public final void addImage(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void removeImage(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void publish(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void resetState() {
    }
}