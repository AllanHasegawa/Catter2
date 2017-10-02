package io.catter2

import android.app.Application
import android.util.Log
import io.catter2.di.AppKodein

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("App", "Initialized")
        AppKodein.initialize(this, useCachedCatApi = true)
    }
}
