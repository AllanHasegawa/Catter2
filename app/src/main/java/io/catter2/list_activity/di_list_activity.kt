package io.catter2.list_activity

import dagger.Component
import dagger.Module
import dagger.Provides
import io.catter2.cat_api.FetchCatImagesUseCase
import io.catter2.cat_api.TheCatAPI
import io.catter2.di.UserDIComponent
import io.catter2.favorites.AddFavoriteUseCase
import io.catter2.favorites.FavoritesRepository
import javax.inject.Scope


@Module
object ListActivityDIModule {
    var testAddFavoriteUseCase: AddFavoriteUseCase? = null
    var testFetchCatImagesUseCase: FetchCatImagesUseCase? = null

    @Provides
    fun provideAddFavoriteUseCase(repository: dagger.Lazy<FavoritesRepository>): AddFavoriteUseCase =
            testAddFavoriteUseCase ?: AddFavoriteUseCase(repository.get())

    @Provides
    fun provideFetchCatImagesUseCase(api: dagger.Lazy<TheCatAPI>): FetchCatImagesUseCase =
            testFetchCatImagesUseCase ?: FetchCatImagesUseCase(api.get())
}

@Component(modules = arrayOf(ListActivityDIModule::class),
        dependencies = arrayOf(UserDIComponent::class))
@ListActivityDIComponent.ListActivityScope
abstract class ListActivityDIComponent {
    @Scope
    annotation class ListActivityScope

    abstract fun inject(activity: ListActivity)

    companion object {
        fun initializeAndInject(activity: ListActivity) {
            DaggerListActivityDIComponent.builder()
                    .userDIComponent(UserDIComponent.instance)
                    .listActivityDIModule(ListActivityDIModule)
                    .build()
                    .inject(activity)
        }
    }
}
