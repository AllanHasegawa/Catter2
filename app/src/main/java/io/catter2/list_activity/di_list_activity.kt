package io.catter2.list_activity

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import io.catter2.cat_api.FetchCatImagesUseCase
import io.catter2.di.UserKodein
import io.catter2.favorites.AddFavoriteUseCase

class ListActivityKodein {
    companion object {
        var testAddFavoriteUseCase: AddFavoriteUseCase? = null
        var testFetchCatImagesUseCase: FetchCatImagesUseCase? = null
    }

    val kodein = Kodein {
        extend(UserKodein.kodein!!)
        bind<AddFavoriteUseCase>() with provider {
            testAddFavoriteUseCase ?: AddFavoriteUseCase(instance())
        }
        bind<FetchCatImagesUseCase>() with provider {
            testFetchCatImagesUseCase ?: FetchCatImagesUseCase(instance())
        }
    }

    fun inject(injector: KodeinInjector) = injector.inject(kodein)
}

