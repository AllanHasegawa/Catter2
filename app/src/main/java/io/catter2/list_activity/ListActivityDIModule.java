package io.catter2.list_activity;

import io.catter2.cat_api.FetchCatImagesUseCase;
import io.catter2.di.UserDIComponent;
import io.catter2.favorites.AddFavoriteUseCase;

public class ListActivityDIModule {
    public static AddFavoriteUseCase testAddFavoriteUseCase;
    public static FetchCatImagesUseCase testFetchCatImagesUseCase;

    private UserDIComponent userDIComponent;

    public ListActivityDIModule(UserDIComponent userDIComponent) {
        this.userDIComponent = userDIComponent;
    }

    AddFavoriteUseCase provideAddFavoriteUseCase() {
        if (testAddFavoriteUseCase != null) {
            return testAddFavoriteUseCase;
        }
        return new AddFavoriteUseCase(userDIComponent.getFavoritesRepository());
    }

    FetchCatImagesUseCase provideFetchCatImagesUseCase() {
        if (testFetchCatImagesUseCase != null) {
            return testFetchCatImagesUseCase;
        }
        return new FetchCatImagesUseCase(userDIComponent.getTheCatAPIService());
    }
}
