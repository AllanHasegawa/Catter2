package io.catter2.di;

import io.catter2.favorites.FavoritesRepository;
import io.catter2.favorites.SharedPrefFavoritesRepository;

public class SharedPrefFavoritesRepoDIModule implements FavoritesRepoDIModule {
    private AppDIComponent appDIComponent;
    private String userToken;

    public SharedPrefFavoritesRepoDIModule(AppDIComponent appDIComponent, String userToken) {
        this.appDIComponent = appDIComponent;
        this.userToken = userToken;
    }

    @Override
    public AppDIComponent getAppDIComponent() {
        return this.appDIComponent;
    }

    @Override
    public FavoritesRepository provideFavoritesRepository() {
        return new SharedPrefFavoritesRepository(appDIComponent.getAppContext(), provideUserToken());
    }

    public String provideUserToken() {
        return this.userToken;
    }
}
