package io.catter2.cat_api;

public interface TheCatAPI {
    interface Callback {
        void response(CatImagesModel response);
    }

    void getCatsWithHats(Callback callback);
}
