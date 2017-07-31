package io.catter2;

import android.app.Application;

import io.catter2.cat_api.CacheTheCatAPI;
import io.catter2.cat_api.RetrofitTheCatAPI;
import io.catter2.cat_api.TheCatAPI;

public class App extends Application {
    private static TheCatAPI theCatAPI;

    public static TheCatAPI getTheCatAPIService() {
        return App.theCatAPI;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TheCatAPI theCatAPIService = new RetrofitTheCatAPI();
        TheCatAPI theCatAPICache = new CacheTheCatAPI(theCatAPIService);
        App.theCatAPI = theCatAPICache;
    }

}
