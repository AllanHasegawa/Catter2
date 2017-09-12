package io.catter2.favorites

open class AddFavoriteUseCase(private val repo: FavoritesRepository) {
    /**
     * @param url
     * @return True if the url was added successfully.
     */
    open fun addFavoriteUrl(url: String?): Boolean {
        if (url == null) {
            return false
        }
        val timeNow = System.currentTimeMillis()
        val model = FavoriteModel(timeNow, url)
        val currentList = repo.addFavorite(model)
        return currentList.contains(model)
    }
}
