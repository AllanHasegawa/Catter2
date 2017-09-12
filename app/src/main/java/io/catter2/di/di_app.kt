package io.catter2.di

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import io.catter2.cat_api.TheCatAPI
import javax.inject.Singleton


@Module
open class AppDIModule {
    @Provides
    @Singleton
    open fun provideAppContext(): Context {
        throw EmptyModuleException()
    }
}


@Module
open class TheCatAPIDIModule {
    @Provides
    @Singleton
    open fun provideTheCatAPI(): TheCatAPI {
        throw EmptyModuleException()
    }
}

@Singleton
@Component(modules = arrayOf(AppDIModule::class, TheCatAPIDIModule::class))
abstract class AppDIComponent {
    companion object {
        var instance: AppDIComponent? = null
            private set

        fun initialize(appDIModule: AppDIModule, theCatAPIDIModule: TheCatAPIDIModule) {
            if (AppDIComponent.instance != null) {
                throw RuntimeException("AppDIComponent already initialized.")
            }
            AppDIComponent.instance = DaggerAppDIComponent
                    .builder()
                    .appDIModule(appDIModule)
                    .theCatAPIDIModule(theCatAPIDIModule)
                    .build()
        }
    }

    abstract fun getAppContext(): Context

    abstract fun getTheCatAPI(): TheCatAPI
}
