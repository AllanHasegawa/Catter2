package io.catter2.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppDIModule {
    @Provides
    @Singleton
    public Context provideAppContext() {
        throw new EmptyModuleException();
    }
}
