package io.catter2.favorites;

import java.util.List;

public class AddFavoriteUseCase {
    private FavoritesRepository repo;

    public AddFavoriteUseCase(FavoritesRepository favoritesRepository) {
        this.repo = favoritesRepository;
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
