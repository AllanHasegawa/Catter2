package io.catter2.di;

import io.catter2.favorites.FavoritesRepository;

public interface FavoritesRepoDIModule {
    AppDIComponent getAppDIComponent();

    FavoritesRepository provideFavoritesRepository();
}
