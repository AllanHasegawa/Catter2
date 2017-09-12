package io.catter2.di

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import io.catter2.favorites.FavoritesRepository
import javax.inject.Named
import javax.inject.Scope


@Scope
annotation class UserScope


@Module
open class FavoritesRepoDIModule {
    @Provides
    @UserScope
    open fun provideFavoritesRepository(
            appContext: Context, @Named("UserToken") userToken: String): FavoritesRepository {
        throw EmptyModuleException()
    }

    @Provides
    @Named("UserToken")
    @UserScope
    open fun provideUserToken(): String {
        throw EmptyModuleException()
    }
}


@UserScope
@Component(modules = arrayOf(FavoritesRepoDIModule::class),
        dependencies = arrayOf(AppDIComponent::class))
abstract class UserDIComponent : AppDIComponent() {
    companion object {
        var instance: UserDIComponent? = null
            private set

        fun initialize(module: FavoritesRepoDIModule) {
            if (UserDIComponent.instance != null) {
                throw RuntimeException("UserDIComponent already initialized.")
            }
            UserDIComponent.instance = DaggerUserDIComponent.builder()
                    .appDIComponent(AppDIComponent.instance)
                    .favoritesRepoDIModule(module)
                    .build()
        }
    }

    abstract fun getFavoritesRepository(): FavoritesRepository?

    fun close() {
        getFavoritesRepository()?.clearChangeListener()
        UserDIComponent.instance = null
    }
}
