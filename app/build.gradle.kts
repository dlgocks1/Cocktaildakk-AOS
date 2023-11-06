import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.compose.cocktaildakk_compose"
        minSdk = 23
        targetSdk = 33
        versionCode = 4
        versionName = "4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KAKAO_API_TOKEN", properties.getProperty("kakao.api.token"))
    }

    buildTypes {
        // release {
        //     minifyEnabled = false
        //     proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        //     signingConfig = signingConfigs.release
        // }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("com.google.firebase:firebase-database-ktx:20.0.4")
    implementation("com.google.firebase:firebase-firestore-ktx:24.3.1")
    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.1")
//    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.1")

    // compose navigate
    implementation("androidx.navigation:navigation-compose:2.5.2")

    // system ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.17.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.43.2")
    kapt("com.google.dagger:hilt-compiler:2.43.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // viewmodel Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // viewPager
    implementation("com.google.accompanist:accompanist-pager:0.20.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.20.1")

    // Room
    implementation("androidx.room:room-runtime:2.4.2")
    kapt("androidx.room:room-compiler:2.4.2")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.4.2")

    // Preview
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")

    // FireBase
    implementation("com.google.firebase:firebase-bom:30.4.1")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage:20.1.0")

    // FlowRow
    implementation("com.google.accompanist:accompanist-flowlayout:0.25.1")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    // numberpciker
    implementation("io.github.ShawnLin013:number-picker:2.4.13")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.5")

    // LocalDateTime 쓰기위해
    implementation("com.jakewharton.threetenabp:threetenabp:1.2.1")

    // Paging Compose
    implementation("androidx.paging:paging-runtime:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha15")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // ImageCropView
    implementation("com.naver.android.helloyako:imagecropview:1.2.3")

    // LiveData
    implementation("androidx.compose.runtime:runtime-livedata:1.1.1")

    // NAVER Map Android SDK 최신 버전도 포함해야 합니다.
    implementation("androidx.appcompat:appcompat:1.0.0")
    implementation("com.naver.maps:map-sdk:3.16.1") {
        exclude(group = "com.android.support")
    }
    implementation("io.github.fornewid:naver-map-compose:1.0.0")
    // FusedLocationSource
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Memory Leak 체크
//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.6")

    // network 라이브러리
    implementation("com.github.skydoves:sandwich:1.2.6")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
