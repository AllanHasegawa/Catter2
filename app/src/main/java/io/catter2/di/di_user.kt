package io.catter2.di

import android.util.Log
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.instanceOrNull
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import io.catter2.favorites.FavoritesRepository
import io.catter2.favorites.SharedPrefFavoritesRepository


object UserKodein {
    var kodein: Kodein? = null
        private set

    fun initialize(userToken: String) {
        kodein?.run { throw RuntimeException("UserKodein already initialized.") }
        kodein = Kodein {
            extend(AppKodein.kodein)
            constant("UserToken") with userToken
            bind<FavoritesRepository>() with singleton {
                Log.i("USER", "Creating FavoritesRepository")
                SharedPrefFavoritesRepository(instance(), instance("UserToken"))
            }
        }
    }

    fun clear() {
        kodein?.instanceOrNull<FavoritesRepository>()?.clearChangeListener()
        kodein = null
    }
}

