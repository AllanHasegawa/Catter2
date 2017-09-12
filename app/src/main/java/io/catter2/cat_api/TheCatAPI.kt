package io.catter2.cat_api

interface TheCatAPI {
    fun getCatsWithHats(callback: (CatImagesModel?) -> Unit)
}
