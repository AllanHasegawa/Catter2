package io.catter2.cat_api

open class FetchCatImagesUseCase(private val catAPI: TheCatAPI) {
    open fun getImagesUrls(callback: (List<String>) -> Unit) {
        catAPI.getCatsWithHats { catImagesModel ->
            val catImagesUrls = catImagesModel
                    ?.catImages
                    ?.map { it.url }
                    ?: emptyList()

            callback(catImagesUrls)
        }
    }
}
