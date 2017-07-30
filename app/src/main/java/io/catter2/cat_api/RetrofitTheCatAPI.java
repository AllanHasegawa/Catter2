package io.catter2.cat_api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitTheCatAPI {
    @GET("images/get?format=xml&results_per_page=20&category=hats")
    Call<CatImagesModel> listCatsWithHat();
}
