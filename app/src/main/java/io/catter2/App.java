package io.catter2;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.catter2.di.AppDIComponent;
import io.catter2.di.AppDIModule;
import io.catter2.di.CachedRetrofitCatApiDIModule;
import io.catter2.di.RetrofitCatApiDIModule;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("App", "Initialized");
        AppDIModule appDIModule = new AppDIModule() {
            @Override
            public Context provideAppContext() {
                return App.this;
            }
        };
        AppDIComponent.initialize(appDIModule, new CachedRetrofitCatApiDIModule());

        // Option if you don't want to cache the cat API responses.
        // AppDIComponent.initialize(appDIModule, new RetrofitCatApiDIModule());
    }
}
