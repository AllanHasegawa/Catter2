package io.catter2.cat_api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

import static android.content.ContentValues.TAG;

public class RetrofitTheCatAPI implements TheCatAPI {
    interface RetrofitCatService {
        @GET("images/get?format=xml&results_per_page=20&category=hats")
        Call<CatImagesModel> listCatsWithHat();
    }

    @Override
    public void getCatsWithHats(final Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thecatapi.com/api/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        RetrofitCatService retrofitCatService = retrofit.create(RetrofitCatService.class);
        retrofitCatService.listCatsWithHat().enqueue(new retrofit2.Callback<CatImagesModel>() {
            @Override
            public void onResponse(Call<CatImagesModel> call, Response<CatImagesModel> response) {
                callback.response(response.body());
            }

            @Override
            public void onFailure(Call<CatImagesModel> call, Throwable t) {
                Log.e(TAG, "Error fetching cat images", t);
                callback.response(null);
            }
        });
    }
}
