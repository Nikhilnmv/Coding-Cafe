# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Firebase classes
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Keep Room classes
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep class * extends androidx.room.RoomDatabase$Callback

# Keep ML Kit classes
-keep class com.google.mlkit.** { *; }
