package io.catter2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    static String SP_USER_FAVORITES_KEY = "user-favorites-urls-%s";
    private static String TAG = "ImagesRvAdapter";
    private static String ARG_USER_TOKEN = "favorites-user-token";

    static public void launch(Context context, String userToken, boolean clearTop) {
        Intent intent = new Intent(context, FavoritesActivity.class);
        intent.putExtra(ARG_USER_TOKEN, userToken);
        if (clearTop) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    private RecyclerView recyclerView;
    private ImagesRvAdapter rvAdapter;
    private String userToken;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.launch(FavoritesActivity.this, userToken);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.favorites_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new ImagesRvAdapter(null);
        recyclerView.setAdapter(rvAdapter);

        String extraUserToken = getIntent().getStringExtra(ARG_USER_TOKEN);
        if (extraUserToken != null) {
            userToken = extraUserToken;
        }
        Log.d(TAG, "UserToken: " + userToken);

        setupFavoritesSharedPref(userToken);
    }

    @Override
    protected void onDestroy() {
        SharedPreferences pref = getSharedPreferences(
                getString(R.string.pref_key_user_data), Context.MODE_PRIVATE);
        pref.unregisterOnSharedPreferenceChangeListener(sharedPrefListener);
        super.onDestroy();
    }

    private void setupFavoritesSharedPref(final String userToken) {
        SharedPreferences pref = getSharedPreferences(
                getString(R.string.pref_key_user_data), Context.MODE_PRIVATE);
        sharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "Key changed: " + key);
                String prefKey = String.format(SP_USER_FAVORITES_KEY, userToken);
                if (key.equals(prefKey)) {
                    updateFavorites(sharedPreferences);
                }
            }
        };
        pref.registerOnSharedPreferenceChangeListener(sharedPrefListener);
        updateFavorites(pref);
    }

    private void updateFavorites(SharedPreferences pref) {
        String prefKey = String.format(SP_USER_FAVORITES_KEY, userToken);
        Log.d(TAG, "PrefKey: " + prefKey);
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

        ArrayList<String> urlsSorted = new ArrayList<>(favorites.size());
        for (FavoriteModel favorite : favorites) {
            urlsSorted.add(favorite.getUrl());
        }

        Log.d(TAG, "Updated favorites: " + urlsSorted.toString());
        rvAdapter.updateImageUrls(new ArrayList<>(urlsSorted));
    }

}
