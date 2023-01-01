package com.compose.cocktaildakk_compose.di

import com.compose.cocktaildakk_compose.BuildConfig
import com.compose.cocktaildakk_compose.network.MapService
import com.compose.cocktaildakk_compose.network.ServiceInterceptor
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoInterceptorOkhttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherInterceptorOkhttpClinet

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OtherRetrofit

    @KakaoInterceptorOkhttpClient
    @Provides
    fun provideKakaoInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ServiceInterceptor("KakaoAK ${BuildConfig.KAKAO_API_TOKEN}"))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @OtherInterceptorOkhttpClinet
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ServiceInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @KakaoRetrofit
    fun provideKaoaoRetrofit(
        @KakaoInterceptorOkhttpClient okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMapService(
        @KakaoRetrofit retrofit: Retrofit,
    ): MapService {
        return retrofit.create(MapService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideUbtClient(
//        closService: ClosService,
//    ): ClosClient {
//        return ClosClient(closService, feedService, cloverService)
//    }
}
