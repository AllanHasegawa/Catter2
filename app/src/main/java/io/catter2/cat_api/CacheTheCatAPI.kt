package io.catter2.cat_api

import android.util.Log

class CacheTheCatAPI(private val service: TheCatAPI) : TheCatAPI {
    companion object {
        private val TAG = "CacheTheCatAPI"
    }

    private var lastResponse: CatImagesModel? = null

    override fun getCatsWithHats(callback: (CatImagesModel?) -> Unit) {
        if (lastResponse != null) {
            Log.d(TAG, "Using cached response.")
            callback(lastResponse)
        } else {
            Log.d(TAG, "Querying a new response.")
            service.getCatsWithHats { catImagesModel ->
                Log.d(TAG, "Saving response to cache.")
                lastResponse = catImagesModel
                callback(catImagesModel)
            }
        }
    }
}
