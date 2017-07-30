package io.catter2.cat_api;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;

public class FetchCatImagesUseCaseTest {
    @Test
    public void testNoImages() throws InterruptedException {
        StubAPI stub = new StubAPI();
        FetchCatImagesUseCase uc = useCase(stub);


        final CountDownLatch latch = new CountDownLatch(1);
        uc.getImagesUrls(new FetchCatImagesUseCase.Callback() {
            @Override
            public void imagesUrls(List<String> urls) {
                Assert.assertThat(urls.size(), equalTo(0));
                latch.countDown();
            }
        });

        stub.respond(new ArrayList<String>());
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testTwoImages() throws InterruptedException {
        StubAPI stub = new StubAPI();
        FetchCatImagesUseCase uc = useCase(stub);

        ArrayList<String> responseUrls = new ArrayList<>();
        responseUrls.add("url0");
        responseUrls.add("url1");

        final CountDownLatch latch = new CountDownLatch(1);
        uc.getImagesUrls(new FetchCatImagesUseCase.Callback() {
            @Override
            public void imagesUrls(List<String> urls) {
                Assert.assertThat(urls.size(), equalTo(2));
                Assert.assertThat(urls.get(0), equalTo("url0"));
                Assert.assertThat(urls.get(1), equalTo("url1"));
                latch.countDown();
            }
        });

        stub.respond(responseUrls);
        latch.await(10, TimeUnit.SECONDS);
    }

    private FetchCatImagesUseCase useCase(TheCatAPI api) {
        return new FetchCatImagesUseCase(api);
    }

    class StubAPI implements TheCatAPI {
        Callback callback;

        @Override
        public void getCatsWithHats(Callback callback) {
            this.callback = callback;
        }

        public void respond(List<String> urls) {
            CatImagesModel response = new CatImagesModel();
            ArrayList<CatImageModel> images = new ArrayList<>();
            for (String url : urls) {
                CatImageModel model = new CatImageModel();
                model.sourceUrl = url;
                model.url = url;
                model.id = url + "id";
                images.add(model);
            }
            response.catImages = images;

            callback.response(response);
        }
    }
}
