package com.reutiliza.app.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\b\u0086\u0081\u0002\u0018\u0000 \u00122\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0012B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011\u00a8\u0006\u0013"}, d2 = {"Lcom/reutiliza/app/data/model/UserLevel;", "", "displayName", "", "minPoints", "", "maxPoints", "(Ljava/lang/String;ILjava/lang/String;II)V", "getDisplayName", "()Ljava/lang/String;", "getMaxPoints", "()I", "getMinPoints", "SEED", "SPROUT", "BRIGHT", "GUARDIAN", "ECO_MASTER", "Companion", "app_debug"})
public enum UserLevel {
    /*public static final*/ SEED /* = new SEED(null, 0, 0) */,
    /*public static final*/ SPROUT /* = new SPROUT(null, 0, 0) */,
    /*public static final*/ BRIGHT /* = new BRIGHT(null, 0, 0) */,
    /*public static final*/ GUARDIAN /* = new GUARDIAN(null, 0, 0) */,
    /*public static final*/ ECO_MASTER /* = new ECO_MASTER(null, 0, 0) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayName = null;
    private final int minPoints = 0;
    private final int maxPoints = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.reutiliza.app.data.model.UserLevel.Companion Companion = null;
    
    UserLevel(java.lang.String displayName, int minPoints, int maxPoints) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    public final int getMinPoints() {
        return 0;
    }
    
    public final int getMaxPoints() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.reutiliza.app.data.model.UserLevel> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/reutiliza/app/data/model/UserLevel$Companion;", "", "()V", "fromPoints", "Lcom/reutiliza/app/data/model/UserLevel;", "points", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.reutiliza.app.data.model.UserLevel fromPoints(int points) {
            return null;
        }
    }
}