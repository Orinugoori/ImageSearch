package com.example.imagesearch.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "KakaoAK %s".format("8c3806ec4289fefdc21f1f8d23fbbff5")
            )
            .build()
        return chain.proceed(newRequest)
    }
}