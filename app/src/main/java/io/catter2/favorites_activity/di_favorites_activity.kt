package io.catter2.favorites_activity

import dagger.Component
import dagger.Module
import dagger.Provides
import io.catter2.di.UserDIComponent
import io.catter2.favorites.FavoritesRepository
import io.catter2.favorites.GetFavoritesUseCase
import javax.inject.Scope

@Module
object FavoritesActivityDIModule {
    var testGetFavoritesUseCase: GetFavoritesUseCase? = null

    @Provides
    fun provideGetFavoritesUseCase(repository: dagger.Lazy<FavoritesRepository>): GetFavoritesUseCase =
            testGetFavoritesUseCase ?: GetFavoritesUseCase(repository.get())
}

@Component(modules = arrayOf(FavoritesActivityDIModule::class),
        dependencies = arrayOf(UserDIComponent::class))
@FavoritesActivityDIComponent.FavoritesActivityScope
abstract class FavoritesActivityDIComponent {
    @Scope
    annotation class FavoritesActivityScope

    abstract fun inject(activity: FavoritesActivity)

    companion object {
        fun initializeAndInject(activity: FavoritesActivity) {
            DaggerFavoritesActivityDIComponent.builder()
                    .userDIComponent(UserDIComponent.instance)
                    .favoritesActivityDIModule(FavoritesActivityDIModule)
                    .build()
                    .inject(activity)
        }
    }
}
