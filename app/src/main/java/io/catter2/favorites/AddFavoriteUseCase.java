package io.catter2.favorites;

import android.content.Context;

import java.util.List;

public class AddFavoriteUseCase {
    private FavoritesRepository repo;

    public AddFavoriteUseCase(Context context, String userToken) {
        repo = new FavoritesRepository(context, userToken);
    }

    /**
     * @param url
     * @return True if the url was added successfully.
     */
    public Boolean addFavoriteUrl(String url) {
        if (url == null) {
            return false;
        }
        long timeNow = System.currentTimeMillis();
        FavoriteModel model = new FavoriteModel(timeNow, url);
        List<FavoriteModel> currentList = repo.addFavorite(model);
        return currentList.contains(model);
    }
}
