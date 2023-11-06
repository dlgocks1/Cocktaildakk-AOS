buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
}

plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id(Plugins.JETBRAINS_KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.DAGGER_HILT_ANDROID) version Versions.HILT apply false
    id(Plugins.GOOGLE_SERVICES) version Versions.GOOGLE_SERVICES apply false
    id(Plugins.FIREBASE_CRASHLYTICS) version Versions.FIREBASE_CRASHLYTICS apply false
}
