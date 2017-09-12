package io.catter2

import android.app.Application
import android.util.Log
import io.catter2.di.AppDIComponent
import io.catter2.di.AppDIModule
import io.catter2.di.CachedRetrofitCatApiDIModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("App", "Initialized")
        val appDIModule = object : AppDIModule() {
            override fun provideAppContext() = this@App
        }
        AppDIComponent.initialize(appDIModule, CachedRetrofitCatApiDIModule())

        // Option if you don't want to cache the cat API responses.
        // AppDIComponent.initialize(appDIModule, new RetrofitCatApiDIModule());
    }
}
