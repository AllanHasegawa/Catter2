package io.catter2.favorites;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.catter2.R;

public class SharedPrefFavoritesRepository implements FavoritesRepository {
    private static String SP_USER_FAVORITES_KEY = "user-favorites-urls-%s";
    private static String TAG = "SharedPrefFavoritesRepo";

    private Context context;
    private String userToken;
    private ChangeListener changeListener;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener;

    public SharedPrefFavoritesRepository(Context context, String userToken) {
        this.context = context;
        this.userToken = userToken;
    }

    @Override
    public List<FavoriteModel> getFavorites() {
        SharedPreferences pref = getPref();
        String prefKey = getFavoritesKey();
        Set<String> entriesSet = pref.getStringSet(prefKey, new HashSet<String>());

        ArrayList<FavoriteModel> favorites = new ArrayList<>(entriesSet.size());
        for (String entry : entriesSet) {
            String[] decoded = entry.split(";");
            favorites.add(new FavoriteModel(Long.valueOf(decoded[1]), decoded[0]));
        }

        Collections.sort(favorites, new Comparator<FavoriteModel>() {
            @Override
            public int compare(FavoriteModel o1, FavoriteModel o2) {
                return (int) (o2.getTimeAdded() - o1.getTimeAdded());
            }
        });

        return favorites;
    }

    @Override
    public List<FavoriteModel> addFavorite(FavoriteModel model) {
        List<FavoriteModel> oldModels = getFavorites();

        boolean hasUrl = false;
        for (FavoriteModel entry : oldModels) {
            if (entry.getUrl().equals(model.getUrl())) {
                hasUrl = true;
                break;
            }
        }

        if (hasUrl) {
            return oldModels;
        }

        ArrayList<FavoriteModel> newList = new ArrayList<>(oldModels);
        newList.add(model);
        saveFavorites(newList);

        return newList;
    }

    @Override
    public void registerChangeListener(final ChangeListener listener) {
        if (this.changeListener != null) {
            throw new RuntimeException("Listener already registered.");
        }
        this.changeListener = listener;
        this.sharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "Key changed: " + key);
                String prefKey = String.format(SP_USER_FAVORITES_KEY, userToken);
                if (key.equals(prefKey)) {
                    changeListener.onFavoritesChanged(getFavorites());
                }
            }
        };
        getPref().registerOnSharedPreferenceChangeListener(this.sharedPrefListener);
    }

    @Override
    public void clearChangeListener() {
        this.changeListener = null;
        if (this.sharedPrefListener != null) {
            getPref().unregisterOnSharedPreferenceChangeListener(this.sharedPrefListener);
            this.sharedPrefListener = null;
        }
    }

    private SharedPreferences getPref() {
        String prefName = context.getString(R.string.pref_key_user_data);
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    private String getFavoritesKey() {
        return String.format(SP_USER_FAVORITES_KEY, userToken);
    }

    private void saveFavorites(ArrayList<FavoriteModel> newList) {
        HashSet<String> newEntries = new HashSet<>(newList.size());

        for (FavoriteModel entry : newList) {
            newEntries.add(entry.getUrl() + ";" + entry.getTimeAdded());
        }

        getPref().edit().putStringSet(getFavoritesKey(), newEntries).apply();
    }
}
