package io.catter2.di

import io.catter2.cat_api.CacheTheCatAPI
import io.catter2.cat_api.RetrofitTheCatAPI
import io.catter2.cat_api.TheCatAPI

open class CachedRetrofitCatApiDIModule : TheCatAPIDIModule() {
    override fun provideTheCatAPI(): TheCatAPI = provideTheCatAPICached(provideTheCatAPIRetrofit())
    protected fun provideTheCatAPIRetrofit(): TheCatAPI = RetrofitTheCatAPI()
    private fun provideTheCatAPICached(catAPI: TheCatAPI): TheCatAPI = CacheTheCatAPI(catAPI)
}
