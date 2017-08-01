package io.catter2.favorites_activity;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import io.catter2.favorites.FavoritesRepository;
import io.catter2.favorites.GetFavoritesUseCase;

@Module
public class FavoritesActivityDIModule {
    public static GetFavoritesUseCase testGetFavoritesUseCase;

    @Provides
    public static GetFavoritesUseCase provideGetFavoritesUseCase(Lazy<FavoritesRepository> repository) {
        if (testGetFavoritesUseCase != null) {
            return testGetFavoritesUseCase;
        }

        return new GetFavoritesUseCase(repository.get());
    }
}
