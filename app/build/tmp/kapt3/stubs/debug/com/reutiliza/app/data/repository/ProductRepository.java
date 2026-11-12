package com.reutiliza.app.data.repository;

import com.reutiliza.app.data.local.SessionManager;
import com.reutiliza.app.data.model.Product;
import com.reutiliza.app.data.model.ProductCategory;
import com.reutiliza.app.data.model.TransactionType;
import com.reutiliza.app.data.remote.CreateProductRequest;
import com.reutiliza.app.data.remote.ReUtilizaApiService;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\u001c\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00100\u000f2\u0006\u0010\u0014\u001a\u00020\u000bJ2\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\b2\u0006\u0010\u0016\u001a\u00020\t2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJD\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00100\b2\b\b\u0002\u0010\u001c\u001a\u00020\u000b2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J4\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\b2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\'H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b(\u0010)R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/reutiliza/app/data/repository/ProductRepository;", "", "api", "Lcom/reutiliza/app/data/remote/ReUtilizaApiService;", "session", "Lcom/reutiliza/app/data/local/SessionManager;", "(Lcom/reutiliza/app/data/remote/ReUtilizaApiService;Lcom/reutiliza/app/data/local/SessionManager;)V", "getProductById", "Lkotlin/Result;", "Lcom/reutiliza/app/data/model/Product;", "id", "", "getProductById-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentProducts", "Lkotlinx/coroutines/flow/Flow;", "", "limit", "", "getUserProducts", "userId", "publishProduct", "product", "imageBytes", "", "publishProduct-0E7RQCE", "(Lcom/reutiliza/app/data/model/Product;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchProducts", "query", "category", "Lcom/reutiliza/app/data/model/ProductCategory;", "transactionType", "Lcom/reutiliza/app/data/model/TransactionType;", "searchProducts-BWLJW6A", "(Ljava/lang/String;Lcom/reutiliza/app/data/model/ProductCategory;Lcom/reutiliza/app/data/model/TransactionType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleFavorite", "", "productId", "isFav", "", "toggleFavorite-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ProductRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.remote.ReUtilizaApiService api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.reutiliza.app.data.local.SessionManager session = null;
    
    @javax.inject.Inject()
    public ProductRepository(@org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.remote.ReUtilizaApiService api, @org.jetbrains.annotations.NotNull()
    com.reutiliza.app.data.local.SessionManager session) {
        super();
    }
    
    /**
     * Emite la lista de productos recientes una vez
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.reutiliza.app.data.model.Product>> getRecentProducts(long limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.reutiliza.app.data.model.Product>> getUserProducts(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
}