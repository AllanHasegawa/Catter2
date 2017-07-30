package io.catter2.cat_api;

import android.util.Log;

public class CacheTheCatAPI implements TheCatAPI {
    private static String TAG = "CacheTheCatAPI";

    private TheCatAPI service;
    private CatImagesModel lastResponse;

    public CacheTheCatAPI(TheCatAPI service) {
        this.service = service;
    }

    @Override
    public void getCatsWithHats(final Callback callback) {
        if (lastResponse != null) {
            Log.d(TAG, "Using cached response.");
            callback.response(lastResponse);
        } else {
            Log.d(TAG, "Querying a new response.");
            service.getCatsWithHats(new Callback() {
                @Override
                public void response(CatImagesModel response) {
                    Log.d(TAG, "Saving response to cache.");
                    lastResponse = response;
                    callback.response(response);
                }
            });
        }
    }
}
