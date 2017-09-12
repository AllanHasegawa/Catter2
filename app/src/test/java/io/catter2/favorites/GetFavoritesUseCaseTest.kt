package io.catter2.favorites

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class GetFavoritesUseCaseTest {
    @Test
    @Throws(InterruptedException::class)
    fun testEmptyList() {
        val mock = mock(FavoritesRepository::class.java)
        val uc = useCase(mock)

        // when
        `when`(mock.getFavorites()).thenReturn(ArrayList())

        val latch = CountDownLatch(1)
        uc.getFavorites { urls ->
            assertThat(urls.size, equalTo(0))
            latch.countDown()
        }
        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testSingleEleList() {
        val mock = mock(FavoritesRepository::class.java)
        val uc = useCase(mock)

        // when
        run {
            val mockList = ArrayList<FavoriteModel>()
            mockList.add(FavoriteModel(10, "url-0"))
            `when`(mock.getFavorites()).thenReturn(mockList)
        }

        val latch = CountDownLatch(1)
        uc.getFavorites { urls ->
            assertThat(urls.size, equalTo(1))
            assertThat(urls[0], equalTo("url-0"))
            latch.countDown()
        }
        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testThreeEleRepoOrder() {
        val mock = mock(FavoritesRepository::class.java)
        val uc = useCase(mock)

        // when
        run {
            val mockList = ArrayList<FavoriteModel>()
            mockList.add(FavoriteModel(10, "url-0"))
            mockList.add(FavoriteModel(12, "url-2"))
            mockList.add(FavoriteModel(11, "url-1"))
            `when`(mock.getFavorites()).thenReturn(mockList)
        }

        val latch = CountDownLatch(1)
        uc.getFavorites { urls ->
            assertThat(urls.size, equalTo(3))
            assertThat(urls[0], equalTo("url-0"))
            assertThat(urls[1], equalTo("url-2"))
            assertThat(urls[2], equalTo("url-1"))
            latch.countDown()
        }
        latch.await(10, TimeUnit.SECONDS)
    }

    private fun useCase(repo: FavoritesRepository) = GetFavoritesUseCase(repo)
}
