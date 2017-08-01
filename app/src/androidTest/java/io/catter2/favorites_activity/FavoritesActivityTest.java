package io.catter2.favorites_activity;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.catter2.R;
import io.catter2.di.FavoritesRepoDIModule;
import io.catter2.di.UserDIComponent;
import io.catter2.espresso_utils.RecyclerViewItemCountAssertion;
import io.catter2.favorites.FavoritesRepository;
import io.catter2.favorites.GetFavoritesUseCase;

import static android.support.test.espresso.Espresso.onView;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class FavoritesActivityTest {
    @Rule
    public ActivityTestRule<FavoritesActivity> activityRule =
            new ActivityTestRule<>(FavoritesActivity.class, true, false /* Do not launch activity! */);

    @BeforeClass
    public static void beforeClass() {
        // Warning: The AppDIComponent is still being created by the 'App' class!
        UserDIComponent.initialize(mock(FavoritesRepoDIModule.class));
    }

    @Test
    public void testThereAreThreeImages() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        expectedUrls.add("url0");
        expectedUrls.add("url1");
        expectedUrls.add("url2");
        setUpGetFavoritesResponse(expectedUrls);

        activityRule.launchActivity(new Intent());

        onView(ViewMatchers.withId(R.id.favorites_rv)).check(new RecyclerViewItemCountAssertion(3));
    }

    @Test
    public void testThereAreZeroImages() {
        final ArrayList<String> expectedUrls = new ArrayList<>();
        setUpGetFavoritesResponse(expectedUrls);

        activityRule.launchActivity(new Intent());

        onView(ViewMatchers.withId(R.id.favorites_rv)).check(new RecyclerViewItemCountAssertion(0));
    }

    private void setUpGetFavoritesResponse(final List<String> urls) {
        FavoritesActivityDIModule.testGetFavoritesUseCase =
                new GetFavoritesUseCase(mock(FavoritesRepository.class)) {
                    @Override
                    public void getFavorites(Callback callback) {
                        callback.favoriteUrlsUpdated(urls);
                    }
                };
    }
}
