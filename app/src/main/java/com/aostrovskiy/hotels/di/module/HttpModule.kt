package com.aostrovskiy.hotels.di.module


import com.aostrovskiy.hotels.BuildConfig
import com.aostrovskiy.hotels.api.HotelAPI
import com.aostrovskiy.hotels.model.Hotel
import com.aostrovskiy.hotels.util.Constant
import com.aostrovskiy.hotels.util.HotelDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class HttpModule {

    @Provides
    @Singleton
    fun provideHttpLogging(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                    })
                    .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit =
            Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): HotelAPI =
            retrofit.create(HotelAPI::class.java)

    @Provides
    @Singleton
    fun provideGsonClient(hotelDeserializer: HotelDeserializer): GsonConverterFactory =
            GsonConverterFactory.create(GsonBuilder()
                    .registerTypeAdapter(Hotel::class.java, hotelDeserializer)
                    .create())

    @Provides
    @Singleton
    fun provideHotelDeserializer(): HotelDeserializer = HotelDeserializer()

}
