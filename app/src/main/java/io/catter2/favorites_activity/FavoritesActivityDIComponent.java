package io.catter2.favorites_activity;

import io.catter2.di.UserDIComponent;

public class FavoritesActivityDIComponent {
    private FavoritesActivityDIModule module;

    public FavoritesActivityDIComponent() {
        this.module = new FavoritesActivityDIModule(UserDIComponent.get());
    }

    public void inject(FavoritesActivity activity) {
        activity.injectGetFavoritesUserCase(module.provideGetFavoritesUseCase());
    }
}
