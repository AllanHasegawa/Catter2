package io.catter2.favorites

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import io.catter2.R

class SharedPrefFavoritesRepository(
        private val context: Context,
        private val userToken: String)
    : FavoritesRepository {

    companion object {
        private val SP_USER_FAVORITES_KEY = "user-favorites-urls-%s"
        private val TAG = "SharedPrefFavoritesRepo"
    }

    private var changeListener: FavoritesRepository.ChangeListener? = null

    private val favoritesKey: String = String.format(SP_USER_FAVORITES_KEY, userToken)
    private val pref: SharedPreferences
        get() {
            val prefName = context.getString(R.string.pref_key_user_data)
            return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        }
    private var sharedPrefListener: SharedPreferences.OnSharedPreferenceChangeListener? = null


    override fun getFavorites(): List<FavoriteModel> {
        return pref.getStringSet(favoritesKey, emptySet())
                .mapNotNull { FavoriteModel.deserialize(it) }
                .sortedBy { it.timeAdded }
    }

    override fun addFavorite(model: FavoriteModel): List<FavoriteModel> {
        return (getFavorites() + model)
                .toSet()
                .also { saveFavorites(it) }
                .toList()
    }

    override fun registerChangeListener(listener: FavoritesRepository.ChangeListener) {
        if (this.changeListener != null) {
            throw RuntimeException("Listener already registered.")
        }
        this.changeListener = listener
        this.sharedPrefListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            Log.d(TAG, "Key changed: " + key)
            val prefKey = String.format(SP_USER_FAVORITES_KEY, userToken)
            if (key == prefKey) {
                changeListener!!.onFavoritesChanged(getFavorites())
            }
        }
        pref.registerOnSharedPreferenceChangeListener(this.sharedPrefListener)
    }

    override fun clearChangeListener() {
        this.changeListener = null
        if (this.sharedPrefListener != null) {
            pref.unregisterOnSharedPreferenceChangeListener(this.sharedPrefListener)
            this.sharedPrefListener = null
        }
    }

    private fun saveFavorites(favorites: Set<FavoriteModel>) {
        favorites
                .map { it.serialize() }
                .toSet()
                .also { set -> pref.edit().putStringSet(favoritesKey, set).apply() }
    }
}

fun FavoriteModel.serialize(): String = "$timeAdded;$url"

fun FavoriteModel.Companion.deserialize(str: String): FavoriteModel? {
    val split = str.split(";")
    if (split.size != 2) {
        return null
    }

    val time = split[0].toLongOrNull() ?: return null
    val url = split[1]

    return FavoriteModel(time, url)
}
