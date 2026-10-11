# Proguard rules for VedicMath
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class com.ayoub.vedicmath.WebAppInterface { *; }
