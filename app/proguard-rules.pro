# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.reutiliza.app.data.model.** { *; }

# Hilt
-keepclassmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Kotlin coroutines
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
