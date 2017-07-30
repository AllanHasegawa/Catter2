package io.catter2.favorites;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class GetFavoritesUseCase {
    public interface Callback {
        void favoriteUrlsUpdated(List<String> favoriteUrls);
    }

    private FavoritesRepository repo;

    public GetFavoritesUseCase(Context context, String userToken) {
        this.repo = new FavoritesRepository(context, userToken);
    }

    /**
     * @param callback Callback returns a list of favorites once during registration and every time
     *                 the favorites are updated.
     */
    public void getFavorites(final Callback callback) {
        callback.favoriteUrlsUpdated(favoritesToUrls(repo.getFavorites()));

        repo.registerChangeListener(new FavoritesRepository.ChangeListener() {
            @Override
            public void onFavoritesChanged(List<FavoriteModel> favorites) {
                callback.favoriteUrlsUpdated(favoritesToUrls(favorites));
            }
        });
    }

    /**
     * Clear needs to be called when the use case if no more needed.
     */
    public void clear() {
        repo.clearChangeListener();
    }

    private List<String> favoritesToUrls(List<FavoriteModel> favorites) {
        ArrayList<String> urls = new ArrayList<>(favorites.size());
        for (FavoriteModel favorite : favorites) {
            urls.add(favorite.getUrl());
        }
        return urls;
    }
}
