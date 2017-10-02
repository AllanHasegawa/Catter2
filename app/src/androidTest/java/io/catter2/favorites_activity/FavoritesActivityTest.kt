package io.catter2.favorites_activity

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import io.catter2.R
import io.catter2.di.UserKodein
import io.catter2.espresso_utils.RecyclerViewItemCountAssertion
import io.catter2.favorites.GetFavoritesUseCase
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
open class FavoritesActivityTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            // Warning: The AppDIComponent is still being created by the 'App' class!
            UserKodein.initialize("user")
        }
    }

    @get:Rule
    var activityRule =
            ActivityTestRule(FavoritesActivity::class.java, true, false /* Do not launch activity! */)

    @Test
    fun testThereAreThreeImages() {
        val expectedUrls = ArrayList<String>()
        expectedUrls.add("url0")
        expectedUrls.add("url1")
        expectedUrls.add("url2")
        setUpGetFavoritesResponse(expectedUrls)

        activityRule.launchActivity(Intent())

        onView(ViewMatchers.withId(R.id.favorites_rv)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun testThereAreZeroImages() {
        val expectedUrls = ArrayList<String>()
        setUpGetFavoritesResponse(expectedUrls)

        activityRule.launchActivity(Intent())

        onView(ViewMatchers.withId(R.id.favorites_rv)).check(RecyclerViewItemCountAssertion(0))
    }

    private fun setUpGetFavoritesResponse(urls: List<String>) {
        FavoritesActivityKodein.testGetFavoritesUseCase =
                object : GetFavoritesUseCase(mock()) {
                    override fun getFavorites(callback: (List<String>) -> Unit) {
                        callback(urls)
                    }
                }
    }
}
