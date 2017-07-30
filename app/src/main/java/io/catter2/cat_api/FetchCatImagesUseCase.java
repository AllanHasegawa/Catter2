package io.catter2.cat_api;

import java.util.ArrayList;
import java.util.List;

public class FetchCatImagesUseCase {
    public interface Callback {
        void imagesUrls(List<String> urls);
    }

    private TheCatAPI catAPI;

    public FetchCatImagesUseCase(TheCatAPI catAPI) {
        this.catAPI = catAPI;
    }

    public void getImagesUrls(final Callback callback) {
        catAPI.getCatsWithHats(new TheCatAPI.Callback() {
            @Override
            public void response(CatImagesModel response) {
                ArrayList<String> imageUrls = new ArrayList<>();
                if (response != null) {
                    for (CatImageModel img : response.catImages) {
                        imageUrls.add(img.url);
                    }
                }
                callback.imagesUrls(imageUrls);
            }
        });
    }
}
