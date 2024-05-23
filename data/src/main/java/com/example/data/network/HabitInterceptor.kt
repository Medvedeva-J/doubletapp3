package com.example.data.network

import okhttp3.Interceptor
import okhttp3.Response

private var TOKEN = "95a82c39-8250-4956-ae49-efa414b87dce"
class HabitInterceptor : Interceptor {

    companion object {
        private const val authHeader = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest
            .newBuilder()
            .header(authHeader, TOKEN)
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}