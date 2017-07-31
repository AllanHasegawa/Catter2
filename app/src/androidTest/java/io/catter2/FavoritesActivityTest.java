package io.catter2;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.catter2.di.AppDIComponent;
import io.catter2.di.FavoritesRepoDIModule;
import io.catter2.di.UserDIComponent;
import io.catter2.espresso_utils.RecyclerViewItemCountAssertion;
import io.catter2.favorites.FavoriteModel;
import io.catter2.favorites.FavoritesRepository;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FavoritesActivityTest {
    @Rule
    public ActivityTestRule<FavoritesActivity> activityRule =
            new ActivityTestRule<>(FavoritesActivity.class);

    @BeforeClass
    public static void beforeClass() {
        UserDIComponent.initialize(new FavoritesRepoDIModule() {
            @Override
            public AppDIComponent getAppDIComponent() {
                return AppDIComponent.get();
            }

            @Override
            public FavoritesRepository provideFavoritesRepository() {
                return new FavoritesRepository() {
                    @Override
                    public List<FavoriteModel> getFavorites() {
                        ArrayList<FavoriteModel> models = new ArrayList<>();
                        models.add(new FavoriteModel(10, "url-10"));
                        models.add(new FavoriteModel(11, "url-11"));
                        models.add(new FavoriteModel(12, "url-12"));
                        return models;
                    }

                    @Override
                    public List<FavoriteModel> addFavorite(FavoriteModel model) {
                        throw new RuntimeException("Not implemented");
                    }

                    @Override
                    public void registerChangeListener(ChangeListener listener) {
                        // NoOp
                    }

                    @Override
                    public void clearChangeListener() {
                        // NoOp
                    }
                };
            }
        });
    }

    @Test
    public void testThereAreThreeImages() {
        onView(withId(R.id.favorites_rv)).check(new RecyclerViewItemCountAssertion(3));
    }
}
