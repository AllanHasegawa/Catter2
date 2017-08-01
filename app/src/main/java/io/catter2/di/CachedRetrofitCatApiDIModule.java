package io.catter2.di;

import io.catter2.cat_api.CacheTheCatAPI;
import io.catter2.cat_api.RetrofitTheCatAPI;
import io.catter2.cat_api.TheCatAPI;

public class CachedRetrofitCatApiDIModule extends TheCatAPIDIModule {
    @Override
    public TheCatAPI provideTheCatAPI() {
        return provideTheCatAPICached(provideTheCatAPIRetrofit());
    }

    public TheCatAPI provideTheCatAPIRetrofit() {
        return new RetrofitTheCatAPI();
    }

    public TheCatAPI provideTheCatAPICached(TheCatAPI catAPI) {
        return new CacheTheCatAPI(catAPI);
    }
}
