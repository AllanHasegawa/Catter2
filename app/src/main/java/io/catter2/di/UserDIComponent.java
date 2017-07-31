package io.catter2.di;

import io.catter2.cat_api.TheCatAPI;
import io.catter2.favorites.FavoritesRepository;

public class UserDIComponent {
    private static UserDIComponent instance;

    public static UserDIComponent get() {
        return instance;
    }

    public static void initialize(FavoritesRepoDIModule module) {
        if (UserDIComponent.get() != null) {
            throw new RuntimeException("UserDIComponent already initialized.");
        }
        UserDIComponent.instance = new UserDIComponent(module);
    }

    private FavoritesRepoDIModule module;
    private FavoritesRepository favoritesRepository;

    private UserDIComponent(FavoritesRepoDIModule module) {
        this.module = module;
    }

    public TheCatAPI getTheCatAPIService() {
        return this.module.getAppDIComponent().getTheCatAPI();
    }

    public FavoritesRepository getFavoritesRepository() {
        if (favoritesRepository == null) {
            favoritesRepository = module.provideFavoritesRepository();
        }
        return favoritesRepository;
    }

    public void close() {
        if (favoritesRepository != null) {
            favoritesRepository.clearChangeListener();
        }
    }
}
