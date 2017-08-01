package io.catter2.favorites_activity;

import javax.inject.Scope;

import dagger.Component;
import io.catter2.di.UserDIComponent;

@Component(modules = FavoritesActivityDIModule.class,
        dependencies = UserDIComponent.class)
@FavoritesActivityDIComponent.FavoritesActivityScope
public abstract class FavoritesActivityDIComponent {
    @Scope
    public @interface FavoritesActivityScope {
    }

    public static void initializeAndInject(FavoritesActivity activity) {
        DaggerFavoritesActivityDIComponent.builder()
                .userDIComponent(UserDIComponent.get())
                .build()
                .inject(activity);
    }

    public abstract void inject(FavoritesActivity activity);
}
