# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidStudioProjects\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# ProGuard configurations for Bugtags
-keepattributes LineNumberTable,SourceFile

-keep class com.bugtags.library.** {*;}
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
-dontwarn com.bugtags.library.**
# End Bugtags
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn android-async-http-1.4.9.jar.**
-keep class android-async-http-1.4.9.jar.**{*;}

-keep class org.apache.http.** {*; }
-dontwarn com.loopj.android.http.**
-keep class com.loopj.android.http.** {*; }
-dontwarn cz.msebera.android.httpclient.**
-keep class cz.msebera.android.httpclient.** {*; }
-dontwarn com.google.gson.jpush.**
-keep class com.google.gson.jpush.** {*; }
-dontwarn java.lang.invoke.**
-keep class java.lang.invoke.** {*; }
-keep class com.sxau.agriculture.bean.** {*; }
-dontwarn java.nio.file.**
-keep class java.nio.file.** {*; }
-dontwarn org.codehaus.mojo.animal_sniffer.**
-keep class org.codehaus.mojo.animal_sniffer.** {*; }

#-libraryjars libs/xUtils-2.6.14.jar
#-libraryjars libs/jpush-android-2.1.5.jar
#-libraryjars libs/Native_Libs2.jar
#-libraryjars libs/android-async-http-1.4.9.jar
