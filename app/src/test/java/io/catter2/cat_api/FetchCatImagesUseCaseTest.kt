package io.catter2.cat_api

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert
import org.junit.Test
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FetchCatImagesUseCaseTest {
    @Test
    @Throws(InterruptedException::class)
    fun testNoImages() {
        val stub = StubAPI()
        val uc = useCase(stub)


        val latch = CountDownLatch(1)

        uc.getImagesUrls { urls ->
            Assert.assertThat(urls.size, equalTo(0))
            latch.countDown()
        }


        stub.respond(ArrayList())
        latch.await(10, TimeUnit.SECONDS)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testTwoImages() {
        val stub = StubAPI()
        val uc = useCase(stub)

        val responseUrls = ArrayList<String>()
        responseUrls.add("url0")
        responseUrls.add("url1")

        val latch = CountDownLatch(1)
        uc.getImagesUrls { urls ->
            Assert.assertThat(urls.size, equalTo(2))
            Assert.assertThat(urls[0], equalTo("url0"))
            Assert.assertThat(urls[1], equalTo("url1"))
            latch.countDown()
        }

        stub.respond(responseUrls)
        latch.await(10, TimeUnit.SECONDS)
    }

    private fun useCase(api: TheCatAPI) = FetchCatImagesUseCase(api)

    internal inner class StubAPI : TheCatAPI {
        var callback: ((CatImagesModel) -> Unit)? = null

        override fun getCatsWithHats(callback: (CatImagesModel?) -> Unit) {
            this.callback = callback
        }


        fun respond(urls: List<String>) {
            val response = CatImagesModel()
            val images = ArrayList<CatImageModel>()
            for (url in urls) {
                val model = CatImageModel()
                model.sourceUrl = url
                model.url = url
                model.id = url + "id"
                images.add(model)
            }
            response.catImages = images

            callback?.invoke(response)
        }
    }
}
