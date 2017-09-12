package io.catter2.favorites

interface FavoritesRepository {
    interface ChangeListener {
        fun onFavoritesChanged(favorites: List<FavoriteModel>)
    }

    /**
     * @return A list of favorites sorted by the time it was added.
     */
    fun getFavorites(): List<FavoriteModel>

    /**
     * @param model
     * @return A list of favorites sorted by the time it was added, with the newly added favorite.
     */
    fun addFavorite(model: FavoriteModel): List<FavoriteModel>

    fun registerChangeListener(listener: ChangeListener)

    fun clearChangeListener()
}
