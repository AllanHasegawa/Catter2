package io.catter2.di;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.catter2.favorites.FavoritesRepository;

@Module
public class FavoritesRepoDIModule {
    @Provides
    @UserScope
    public FavoritesRepository provideFavoritesRepository(
            Context appContext, @Named("UserToken") String userToken) {
        throw new EmptyModuleException();
    }

    @Provides
    @Named("UserToken")
    @UserScope
    public String provideUserToken() {
        throw new EmptyModuleException();
    }
}
