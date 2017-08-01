package io.catter2.list_activity;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import io.catter2.cat_api.FetchCatImagesUseCase;
import io.catter2.cat_api.TheCatAPI;
import io.catter2.favorites.AddFavoriteUseCase;
import io.catter2.favorites.FavoritesRepository;

@Module
public class ListActivityDIModule {
    public static AddFavoriteUseCase testAddFavoriteUseCase;
    public static FetchCatImagesUseCase testFetchCatImagesUseCase;

    @Provides
    public static AddFavoriteUseCase provideAddFavoriteUseCase(Lazy<FavoritesRepository> repository) {
        if (testAddFavoriteUseCase != null) {
            return testAddFavoriteUseCase;
        }
        return new AddFavoriteUseCase(repository.get());
    }

    @Provides
    public static FetchCatImagesUseCase provideFetchCatImagesUseCase(Lazy<TheCatAPI> api) {
        if (testFetchCatImagesUseCase != null) {
            return testFetchCatImagesUseCase;
        }
        return new FetchCatImagesUseCase(api.get());
    }
}
