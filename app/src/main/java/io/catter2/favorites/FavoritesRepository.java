package io.catter2.favorites;

import java.util.List;

public interface FavoritesRepository {
    interface ChangeListener {
        void onFavoritesChanged(List<FavoriteModel> favorites);
    }

    /**
     * @return A list of favorites sorted by the time it was added.
     */
    List<FavoriteModel> getFavorites();

    /**
     * @param model
     * @return A list of favorites sorted by the time it was added, with the newly added favorite.
     */
    List<FavoriteModel> addFavorite(FavoriteModel model);

    void registerChangeListener(final ChangeListener listener);

    void clearChangeListener();
}
