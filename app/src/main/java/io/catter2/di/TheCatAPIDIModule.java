package io.catter2.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.catter2.cat_api.TheCatAPI;

@Module
public class TheCatAPIDIModule {
    @Provides
    @Singleton
    public TheCatAPI provideTheCatAPI() {
        throw new EmptyModuleException();
    }
}
