package com.example.bitspender.di

import com.example.bitspender.BuildConfig
import com.example.bitspender.data.remote.BtcRateApi
import com.example.bitspender.data.remote.errorhandling.ApiResponseCallAdapterFactory
import com.example.bitspender.data.remote.errorhandling.DefaultErrorHandler
import com.example.bitspender.data.remote.errorhandling.ErrorHandler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    private val BASE_URL = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideErrorHandler(): DefaultErrorHandler = DefaultErrorHandler()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
        errorHandler: ErrorHandler
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory(errorHandler))
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideBtcRateApi(retrofit: Retrofit): BtcRateApi =
        retrofit.create(BtcRateApi::class.java)
}
