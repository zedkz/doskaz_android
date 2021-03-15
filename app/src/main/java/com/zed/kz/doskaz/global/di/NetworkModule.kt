package com.zed.kz.doskaz.global.di

import com.zed.kz.doskaz.BuildConfig
import com.zed.kz.doskaz.global.di.ServiceProperties.AUTH_HEADER
import com.zed.kz.doskaz.global.di.ServiceProperties.AUTH_PREFIX
import com.zed.kz.doskaz.global.di.ServiceProperties.LANG_HEADER
import com.zed.kz.doskaz.global.di.ServiceProperties.SERVER_URL
import com.zed.kz.doskaz.global.service.ServerService
import com.zed.kz.doskaz.global.utils.LocalStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<ServerService>(get(), SERVER_URL) }
}


object ServiceProperties {
    const val SERVER_URL = "https://doskaz.kz"
    //const val SERVER_URL = "https://doskaz.vps3.zed.kz/"

    const val AUTH_HEADER = "Authorization"
    const val AUTH_PREFIX = "Bearer"
    const val LANG_HEADER = "Accept-language"
}

fun createOkHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
    okHttpClientBuilder.callTimeout(5, TimeUnit.MINUTES)
    okHttpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
    okHttpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)

    okHttpClientBuilder.addInterceptor { chain ->
        var request = chain.request()
        val url = request.url().newBuilder()
        request = request.newBuilder()
            .addHeader(AUTH_HEADER, "$AUTH_PREFIX ${LocalStorage.getAccessToken()}")
            .addHeader(LANG_HEADER, LocalStorage.getLangForApi())
            .url(url.build())
            .build()
        chain.proceed(request)
    }

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
    }
    return okHttpClientBuilder.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}
