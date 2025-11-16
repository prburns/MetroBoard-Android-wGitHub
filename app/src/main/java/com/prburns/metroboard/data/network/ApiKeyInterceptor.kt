package com.prburns.metroboard.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithApiKey = originalRequest.newBuilder()
            .addHeader("api_key", WMATAApiService.API_KEY)
            .build()
        return chain.proceed(requestWithApiKey)
    }
}
