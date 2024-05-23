package com.example.data.di

import com.example.data.network.Client
import com.example.data.network.HabitServerAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return Client.getHttpClient()
    }

    @Singleton
    @Provides
    fun provideHabitServerAPI(client: OkHttpClient) : HabitServerAPI {
        return Client.getHabitServerAPI(client)
    }
}