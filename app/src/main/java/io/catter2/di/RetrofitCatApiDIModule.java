package io.catter2.di;

import io.catter2.cat_api.TheCatAPI;

public class RetrofitCatApiDIModule extends CachedRetrofitCatApiDIModule {
    @Override
    public TheCatAPI provideTheCatAPI() {
        return super.provideTheCatAPIRetrofit();
    }
}
