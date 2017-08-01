package io.catter2.di;

import android.content.Context;

import io.catter2.favorites.FavoritesRepository;
import io.catter2.favorites.SharedPrefFavoritesRepository;

public class SharedPrefFavoritesRepoDIModule extends FavoritesRepoDIModule {
    private String userToken;

    public SharedPrefFavoritesRepoDIModule(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public FavoritesRepository provideFavoritesRepository(Context appContext, String userToken) {
        return new SharedPrefFavoritesRepository(appContext, userToken);
    }

    @Override
    public String provideUserToken() {
        return this.userToken;
    }
}
