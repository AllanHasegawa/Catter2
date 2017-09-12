package io.catter2.di

import io.catter2.cat_api.TheCatAPI

class RetrofitCatApiDIModule : CachedRetrofitCatApiDIModule() {
    override fun provideTheCatAPI(): TheCatAPI = super.provideTheCatAPIRetrofit()
}
