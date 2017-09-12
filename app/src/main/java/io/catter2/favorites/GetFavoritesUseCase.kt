package io.catter2.favorites

open class GetFavoritesUseCase(private val repo: FavoritesRepository) {
    /**
     * @param callback Callback returns a list of favorites once during registration and every time
     * the favorites are updated.
     */
    open fun getFavorites(callback: (List<String>) -> Unit) {
        callback(favoritesToUrls(repo.getFavorites()))
        repo.registerChangeListener(object : FavoritesRepository.ChangeListener {
            override fun onFavoritesChanged(favorites: List<FavoriteModel>) {
                callback(favoritesToUrls(favorites))
            }
        })
    }

    /**
     * Clear needs to be called when the use case is no more needed.
     */
    fun clear() {
        repo.clearChangeListener()
    }

    private fun favoritesToUrls(favorites: List<FavoriteModel>) = favorites.map { it.url }
}
