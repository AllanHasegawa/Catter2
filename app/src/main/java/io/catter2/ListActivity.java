package io.catter2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.catter2.cat_api.CatImageModel;
import io.catter2.cat_api.CatImagesModel;
import io.catter2.cat_api.RetrofitTheCatAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ListActivity extends AppCompatActivity {
    private static String TAG = "List";
    private static String ARG_USER_TOKEN = "list-user-token";

    static public void launch(Context context, String userToken) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(ARG_USER_TOKEN, userToken);
        context.startActivity(intent);
    }

    private String userToken;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String extraUserToken = getIntent().getStringExtra(ARG_USER_TOKEN);
        if (extraUserToken != null) {
            userToken = extraUserToken;
        }
        Log.d(TAG, "UserToken: " + userToken);

        recyclerView = (RecyclerView) findViewById(R.id.list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ImagesRvAdapter adapter = new ImagesRvAdapter(new ImagesRvAdapter.ImageOnClick() {
            @Override
            public void imageClicked(ImageView view, String url) {
                addUrlToUserFavoritesList(view, url);
            }
        });
        recyclerView.setAdapter(adapter);


        fetchCatImages(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FavoritesActivity.launch(this, userToken, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchCatImages(final ImagesRvAdapter adapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thecatapi.com/api/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        RetrofitTheCatAPI retrofitTheCatApi = retrofit.create(RetrofitTheCatAPI.class);
        retrofitTheCatApi.listCatsWithHat().enqueue(new Callback<CatImagesModel>() {
            @Override
            public void onResponse(Call<CatImagesModel> call, Response<CatImagesModel> response) {
                ArrayList<String> imageUrls = new ArrayList<>();
                if (response.body().catImages != null) {
                    for (CatImageModel img : response.body().catImages) {
                        Log.d(TAG, "Found: " + img.url);
                        imageUrls.add(img.url);
                    }
                }
                adapter.updateImageUrls(imageUrls);
            }

            @Override
            public void onFailure(Call<CatImagesModel> call, Throwable t) {
                Log.e(TAG, "Error fetching cat images", t);
            }
        });
    }

    private void addUrlToUserFavoritesList(ImageView view, String url) {
        SharedPreferences pref = getSharedPreferences(
                getString(R.string.pref_key_user_data), Context.MODE_PRIVATE);
        String prefKey = String.format(FavoritesActivity.SP_USER_FAVORITES_KEY, userToken);
        Log.d(TAG, "Pref key: " + prefKey);
        Set<String> oldUrls = pref.getStringSet(prefKey, new HashSet<String>());

        boolean hasUrl = false;
        for (String entry : oldUrls) {
            String oldUrl = entry.split(";")[0];
            if (oldUrl.equals(url)) {
                hasUrl = true;
                break;
            }
        }

        if (hasUrl) {
            Snackbar.make(recyclerView, R.string.list_user_favorite_url_already_in, Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }

        HashSet<String> newUrls = new HashSet<>(oldUrls);
        long timeNow = System.currentTimeMillis();
        String timeNowStr = String.valueOf(timeNow);
        newUrls.add(url + ";" + timeNowStr);

        pref.edit().putStringSet(prefKey, newUrls).apply();

        new ParticleSystem(ListActivity.this, 500, R.mipmap.azunyan_2, 2000)
                .setAcceleration(0.0005f, 90)
                .setSpeedRange(0.2f, 0.5f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 180)
                .setFadeOut(500)
                .setScaleRange(0.1f, 1.0f)
                .oneShot(view, 100);

        Snackbar.make(recyclerView, R.string.list_user_favorite_url_added_success, Snackbar.LENGTH_SHORT)
                .show();
    }
}
