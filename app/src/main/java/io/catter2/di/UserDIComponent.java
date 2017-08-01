package io.catter2.di;

import dagger.Component;
import io.catter2.favorites.FavoritesRepository;

@UserScope
@Component(modules = FavoritesRepoDIModule.class,
        dependencies = AppDIComponent.class)
public abstract class UserDIComponent extends AppDIComponent {
    private static UserDIComponent instance;

    public static UserDIComponent get() {
        return instance;
    }

    public static void initialize(FavoritesRepoDIModule module) {
        if (UserDIComponent.get() != null) {
            throw new RuntimeException("UserDIComponent already initialized.");
        }
        UserDIComponent.instance = DaggerUserDIComponent.builder()
                .appDIComponent(AppDIComponent.get())
                .favoritesRepoDIModule(module)
                .build();
    }

    public abstract FavoritesRepository getFavoritesRepository();

    public void close() {
        final FavoritesRepository favoritesRepository = getFavoritesRepository();
        if (favoritesRepository != null) {
            favoritesRepository.clearChangeListener();
        }
        UserDIComponent.instance = null;
    }
}
