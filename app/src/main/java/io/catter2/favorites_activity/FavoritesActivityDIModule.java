package io.catter2.favorites_activity;

import io.catter2.di.UserDIComponent;
import io.catter2.favorites.GetFavoritesUseCase;

public class FavoritesActivityDIModule {
    public static GetFavoritesUseCase testGetFavoritesUseCase;

    private UserDIComponent userDIComponent;

    public FavoritesActivityDIModule(UserDIComponent userDIComponent) {
        this.userDIComponent = userDIComponent;
    }

    GetFavoritesUseCase provideGetFavoritesUseCase() {
        if (testGetFavoritesUseCase != null) {
            return testGetFavoritesUseCase;
        }

        return new GetFavoritesUseCase(userDIComponent.getFavoritesRepository());
    }
}
