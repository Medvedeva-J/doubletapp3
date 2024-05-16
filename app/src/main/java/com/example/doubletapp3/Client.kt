package com.example.doubletapp3

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    companion object {
        private const val baseUrl = "https://droid-test-server.doubletapp.ru/api/"

        fun getHttpClient(): OkHttpClient {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient()
                .newBuilder()
                .addInterceptor(HabitInterceptor())
                .addInterceptor(logInterceptor)
                .build()
        }

        fun getHabitServerAPI(client: OkHttpClient): HabitServerAPI{
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(HabitServerAPI::class.java)
        }
    }
}
