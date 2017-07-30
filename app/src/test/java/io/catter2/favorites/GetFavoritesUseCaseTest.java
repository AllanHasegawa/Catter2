package io.catter2.favorites;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetFavoritesUseCaseTest {
    @Test
    public void testEmptyList() throws InterruptedException {
        FavoritesRepository mock = mock(FavoritesRepository.class);
        GetFavoritesUseCase uc = useCase(mock);

        // when
        when(mock.getFavorites()).thenReturn(new ArrayList<FavoriteModel>());

        final CountDownLatch latch = new CountDownLatch(1);
        uc.getFavorites(new GetFavoritesUseCase.Callback() {
            @Override
            public void favoriteUrlsUpdated(List<String> favoriteUrls) {
                assertThat(favoriteUrls.size(), equalTo(0));
                latch.countDown();
            }
        });
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testSingleEleList() throws InterruptedException {
        FavoritesRepository mock = mock(FavoritesRepository.class);
        GetFavoritesUseCase uc = useCase(mock);

        // when
        {
            ArrayList<FavoriteModel> mockList = new ArrayList<>();
            mockList.add(new FavoriteModel(10, "url-0"));
            when(mock.getFavorites()).thenReturn(mockList);
        }

        final CountDownLatch latch = new CountDownLatch(1);
        uc.getFavorites(new GetFavoritesUseCase.Callback() {
            @Override
            public void favoriteUrlsUpdated(List<String> favoriteUrls) {
                assertThat(favoriteUrls.size(), equalTo(1));
                assertThat(favoriteUrls.get(0), equalTo("url-0"));
                latch.countDown();
            }
        });
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testThreeEleRepoOrder() throws InterruptedException {
        FavoritesRepository mock = mock(FavoritesRepository.class);
        GetFavoritesUseCase uc = useCase(mock);

        // when
        {
            ArrayList<FavoriteModel> mockList = new ArrayList<>();
            mockList.add(new FavoriteModel(10, "url-0"));
            mockList.add(new FavoriteModel(12, "url-2"));
            mockList.add(new FavoriteModel(11, "url-1"));
            when(mock.getFavorites()).thenReturn(mockList);
        }

        final CountDownLatch latch = new CountDownLatch(1);
        uc.getFavorites(new GetFavoritesUseCase.Callback() {
            @Override
            public void favoriteUrlsUpdated(List<String> favoriteUrls) {
                assertThat(favoriteUrls.size(), equalTo(3));
                assertThat(favoriteUrls.get(0), equalTo("url-0"));
                assertThat(favoriteUrls.get(1), equalTo("url-2"));
                assertThat(favoriteUrls.get(2), equalTo("url-1"));
                latch.countDown();
            }
        });
        latch.await(10, TimeUnit.SECONDS);
    }

    private GetFavoritesUseCase useCase(FavoritesRepository repo) {
        return new GetFavoritesUseCase(repo);
    }
}
