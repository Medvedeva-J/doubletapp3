package com.example

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.di.AppComponent
import com.example.di.DaggerAppComponent


class MyApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        var mInstance: MyApplication? = null

        fun getInstance(): MyApplication? = mInstance

        fun getContext(): Context? = mInstance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        appComponent = DaggerAppComponent
            .builder()
            .context(context = this)
            .build()

        mInstance = this
    }
}