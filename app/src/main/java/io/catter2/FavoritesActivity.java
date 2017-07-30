package io.catter2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import io.catter2.favorites.GetFavoritesUseCase;

public class FavoritesActivity extends AppCompatActivity {

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

    private GetFavoritesUseCase getFavoritesUseCase;

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

        getFavoritesUseCase = new GetFavoritesUseCase(this, userToken);
        getFavoritesUseCase.getFavorites(new GetFavoritesUseCase.Callback() {
            @Override
            public void favoriteUrlsUpdated(List<String> favoriteUrls) {
                Log.d(TAG, "Updated favorites: " + favoriteUrls.toString());
                rvAdapter.updateImageUrls(favoriteUrls);
            }
        });
    }

    @Override
    protected void onDestroy() {
        getFavoritesUseCase.clear();
        getFavoritesUseCase = null;
        super.onDestroy();
    }
}
