package io.catter2.di;

import android.content.Context;

import io.catter2.cat_api.TheCatAPI;

public class AppDIComponent {
    private static AppDIComponent instance;

    public static AppDIComponent get() {
        return AppDIComponent.instance;
    }

    public static void initialize(AppDIModule appDIModule, TheCatAPIDIModule theCatAPIDIModule) {
        if (AppDIComponent.get() != null) {
            throw new RuntimeException("AppDIComponent already initialized.");
        }
        AppDIComponent.instance = new AppDIComponent(appDIModule, theCatAPIDIModule);
    }

    private AppDIModule appDIModule;
    private TheCatAPIDIModule theCatAPIDIModule;

    private Context appContext;
    private TheCatAPI theCatAPI;

    public AppDIComponent(AppDIModule appDIModule, TheCatAPIDIModule theCatAPIDIModule) {
        this.appDIModule = appDIModule;
        this.theCatAPIDIModule = theCatAPIDIModule;
    }

    public Context getAppContext() {
        if (appContext == null) {
            appContext = appDIModule.provideAppContext();
        }
        return appContext;
    }

    public TheCatAPI getTheCatAPI() {
        if (theCatAPI == null) {
            theCatAPI = theCatAPIDIModule.provideTheCatAPI();
        }
        return theCatAPI;
    }
}
