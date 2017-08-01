package io.catter2.list_activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.catter2.R;
import io.catter2.cat_api.FetchCatImagesUseCase;
import io.catter2.cat_api.TheCatAPI;
import io.catter2.espresso_utils.RecyclerViewItemCountAssertion;
import io.catter2.favorites.AddFavoriteUseCase;
import io.catter2.favorites.FavoritesRepository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {
    @Rule
    public ActivityTestRule<ListActivity> activityRule =
            new ActivityTestRule<>(ListActivity.class, true, false /* Do not launch activity! */);

    @Test
    public void testThereAreThreeImages() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("url0");
        expectedUrls.add("url1");
        expectedUrls.add("url2");
        setUpListCatImages(expectedUrls);

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.list_rv)).check(new RecyclerViewItemCountAssertion(3));
    }

    @Test
    public void testImageTouchAddFavorite() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("url0");
        setUpListCatImages(expectedUrls);

        AddFavoriteUseCase mock = mock(AddFavoriteUseCase.class);
        when(mock.addFavoriteUrl("url0")).thenReturn(true);
        ListActivityDIModule.testAddFavoriteUseCase = mock;

        activityRule.launchActivity(new Intent());

        // We have to wait because we are not mocking everything yet.
        // Picasso still trying to fetch images from the network!
        SystemClock.sleep(500);

        onView(withId(R.id.list_rv)).check(new RecyclerViewItemCountAssertion(1));
        onView(withId(R.id.list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        verify(mock, times(1)).addFavoriteUrl("url0");
    }

    private void setUpListCatImages(final List<String> urls) {
        ListActivityDIModule.testFetchCatImagesUseCase =
                new FetchCatImagesUseCase(mock(TheCatAPI.class)) {
                    @Override
                    public void getImagesUrls(Callback callback) {
                        callback.imagesUrls(urls);
                    }
                };
        ListActivityDIModule.testAddFavoriteUseCase = // NoOp
                new AddFavoriteUseCase(mock(FavoritesRepository.class));
    }
}
