package io.catter2.list_activity

import android.content.Intent
import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.catter2.R
import io.catter2.cat_api.FetchCatImagesUseCase
import io.catter2.di.UserDIComponent
import io.catter2.espresso_utils.RecyclerViewItemCountAssertion
import io.catter2.favorites.AddFavoriteUseCase
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.util.*

@RunWith(AndroidJUnit4::class)
class ListActivityTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            // Warning: The AppDIComponent is still being created by the 'App' class!
            UserDIComponent.initialize(mock())
        }
    }

    @get:Rule
    var activityRule =
            ActivityTestRule(ListActivity::class.java, true, false /* Do not launch activity! */)

    @Test
    fun testThereAreThreeImages() {
        val expectedUrls = ArrayList<String>()
        expectedUrls.add("url0")
        expectedUrls.add("url1")
        expectedUrls.add("url2")
        setUpListCatImages(expectedUrls)

        activityRule.launchActivity(Intent())

        onView(withId(R.id.list_rv)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun testImageTouchAddFavorite() {
        val expectedUrls = ArrayList<String>()
        expectedUrls.add("url0")
        setUpListCatImages(expectedUrls)

        val mock = mock<AddFavoriteUseCase> {
            on { addFavoriteUrl("url0") } doReturn true
        }
        ListActivityDIModule.testAddFavoriteUseCase = mock

        activityRule.launchActivity(Intent())

        // We have to wait because we are not mocking everything yet.
        // Picasso still trying to fetch images from the network!
        SystemClock.sleep(500)

        onView(withId(R.id.list_rv)).check(RecyclerViewItemCountAssertion(1))
        onView(withId(R.id.list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        verify(mock, times(1)).addFavoriteUrl("url0")
    }

    private fun setUpListCatImages(urls: List<String>) {
        ListActivityDIModule.testFetchCatImagesUseCase = object : FetchCatImagesUseCase(mock()) {
            override fun getImagesUrls(callback: (List<String>) -> Unit) {
                callback(urls)
            }
        }
        ListActivityDIModule.testAddFavoriteUseCase = // NoOp
                AddFavoriteUseCase(mock())
    }
}
