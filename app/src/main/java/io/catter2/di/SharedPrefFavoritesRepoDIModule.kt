package io.catter2.di

import android.content.Context

import io.catter2.favorites.FavoritesRepository
import io.catter2.favorites.SharedPrefFavoritesRepository

class SharedPrefFavoritesRepoDIModule(private val userToken: String) : FavoritesRepoDIModule() {
    override fun provideFavoritesRepository(appContext: Context, userToken: String): FavoritesRepository =
            SharedPrefFavoritesRepository(appContext, userToken)

    override fun provideUserToken(): String = this.userToken
}
