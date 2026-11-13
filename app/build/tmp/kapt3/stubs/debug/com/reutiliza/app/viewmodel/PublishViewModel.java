package com.reutiliza.app.viewmodel;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.reutiliza.app.data.location.LocationManager;
import com.reutiliza.app.data.location.UserLocation;
import com.reutiliza.app.data.model.Product;
import com.reutiliza.app.data.model.ProductCategory;
import com.reutiliza.app.data.model.ProductCondition;
import com.reutiliza.app.data.model.TransactionType;
import com.reutiliza.app.data.repository.AuthRepository;
import com.reutiliza.app.data.repository.ProductRepository;
import com.reutiliza.app.data.repository.UserRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001eJ\u000e\u0010.\u001a\u00020,2\u0006\u0010/\u001a\u000200J\u000e\u00101\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001eJ\u0006\u00102\u001a\u00020,R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0013R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013R\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0013R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\r0&\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0019\u0010)\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0&\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010(R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00063"}, d2 = {"Lcom/reutiliza/app/viewmodel/PublishViewModel;", "Landroidx/lifecycle/ViewModel;", "productRepo", "Lcom/reutiliza/app/data/repository/ProductRepository;", "userRepo", "Lcom/reutiliza/app/data/repository/UserRepository;", "authRepo", "Lcom/reutiliza/app/data/repository/AuthRepository;", "locationManager", "Lcom/reutiliza/app/data/location/LocationManager;", "(Lcom/reutiliza/app/data/repository/ProductRepository;Lcom/reutiliza/app/data/repository/UserRepository;Lcom/reutiliza/app/data/repository/AuthRepository;Lcom/reutiliza/app/data/location/LocationManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/reutiliza/app/viewmodel/PublishUiState;", "_userLocation", "Lcom/reutiliza/app/data/location/UserLocation;", "description", "", "getDescription", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "price", "getPrice", "selectedCategory", "Lcom/reutiliza/app/data/model/ProductCategory;", "getSelectedCategory", "selectedCondition", "Lcom/reutiliza/app/data/model/ProductCondition;", "getSelectedCondition", "selectedImages", "", "Landroid/net/Uri;", "getSelectedImages", "selectedTransactionType", "Lcom/reutiliza/app/data/model/TransactionType;", "getSelectedTransactionType", "title", "getTitle", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "userLocation", "getUserLocation", "addImage", "", "uri", "publish", "context", "Landroid/content/Context;", "removeImage", "resetState", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PublishViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.ProductRepository productRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.UserRepository userRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.repository.AuthRepository authRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.location.LocationManager locationManager = null;
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
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.reutiliza.app.data.location.UserLocation> _userLocation = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.reutiliza.app.data.location.UserLocation> userLocation = null;
    
    @javax.inject.Inject()
    public PublishViewModel(@org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.ProductRepository productRepo, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.UserRepository userRepo, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.repository.AuthRepository authRepo, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.location.LocationManager locationManager) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.reutiliza.app.data.location.UserLocation> getUserLocation() {
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