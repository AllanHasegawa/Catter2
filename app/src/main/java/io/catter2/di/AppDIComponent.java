package io.catter2.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.catter2.cat_api.TheCatAPI;

@Singleton
@Component(modules = {AppDIModule.class, TheCatAPIDIModule.class})
public abstract class AppDIComponent {
    private static AppDIComponent instance;

    public static AppDIComponent get() {
        return AppDIComponent.instance;
    }

    public static void initialize(AppDIModule appDIModule, TheCatAPIDIModule theCatAPIDIModule) {
        if (AppDIComponent.get() != null) {
            throw new RuntimeException("AppDIComponent already initialized.");
        }
        AppDIComponent.instance = DaggerAppDIComponent
                .builder()
                .appDIModule(appDIModule)
                .theCatAPIDIModule(theCatAPIDIModule)
                .build();
    }

    abstract public Context getAppContext();

    abstract public TheCatAPI getTheCatAPI();
}
