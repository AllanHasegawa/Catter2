package io.catter2.favorites_activity

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import io.catter2.di.UserKodein
import io.catter2.favorites.GetFavoritesUseCase

class FavoritesActivityKodein {
    companion object {
        var testGetFavoritesUseCase: GetFavoritesUseCase? = null
    }

    val kodein = Kodein {
        extend(UserKodein.kodein!!)
        bind<GetFavoritesUseCase>() with provider {
            testGetFavoritesUseCase ?: GetFavoritesUseCase(instance())
        }
    }

    fun inject(injector: KodeinInjector) = injector.inject(kodein)
}

