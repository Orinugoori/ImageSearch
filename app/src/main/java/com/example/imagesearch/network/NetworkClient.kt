package com.example.imagesearch.network

import com.example.imagesearch.data.remote.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL = "https://dapi.kakao.com/v2/search/"

    private val okHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val networkClient : ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

}