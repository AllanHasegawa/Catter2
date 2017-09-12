package io.catter2.cat_api

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

class RetrofitTheCatAPI : TheCatAPI {
    internal interface RetrofitCatService {
        @GET("images/get?format=xml&results_per_page=20&category=hats")
        fun listCatsWithHat(): Call<CatImagesModel>
    }

    override fun getCatsWithHats(callback: (CatImagesModel?) -> Unit) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://thecatapi.com/api/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
        val retrofitCatService = retrofit.create(RetrofitCatService::class.java)
        retrofitCatService.listCatsWithHat().enqueue(object : retrofit2.Callback<CatImagesModel> {
            override fun onResponse(call: Call<CatImagesModel>, response: Response<CatImagesModel>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<CatImagesModel>, t: Throwable) {
                Log.e(TAG, "Error fetching cat images", t)
                callback(null)
            }
        })
    }
}
