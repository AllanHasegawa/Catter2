package io.catter2;

import android.app.Application;

import io.catter2.cat_api.CacheTheCatAPI;
import io.catter2.cat_api.RetrofitTheCatAPI;
import io.catter2.cat_api.TheCatAPI;
import io.catter2.favorites.FavoritesRepository;
import io.catter2.favorites.SharedPrefFavoritesRepository;

public class App extends Application {
    private static TheCatAPI theCatAPI;
    private static FavoritesRepository favoritesRepository;

    public static TheCatAPI getTheCatAPIService() {
        return App.theCatAPI;
    }

    public static FavoritesRepository getFavoritesRepository() {
        return App.favoritesRepository;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        TheCatAPI theCatAPIService = new RetrofitTheCatAPI();
        TheCatAPI theCatAPICache = new CacheTheCatAPI(theCatAPIService);
        App.theCatAPI = theCatAPICache;
    }

    public void initializeFavoritesRepository(String userToken) {
        if (App.favoritesRepository != null) {
            throw new RuntimeException("FavoritesRepository already initialized.");
        }
        App.favoritesRepository = new SharedPrefFavoritesRepository(this, userToken);
    }

    public void destroyFavoritesRepository() {
        if (App.favoritesRepository != null) {
            App.favoritesRepository.clearChangeListener();
            App.favoritesRepository = null;
        }
    }
}
