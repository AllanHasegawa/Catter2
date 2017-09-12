package io.catter2.favorites

data class FavoriteModel(val timeAdded: Long, val url: String) {
    companion object {}

    override fun equals(other: Any?) = (other as? FavoriteModel)?.url == url
    override fun hashCode() = url.hashCode()
}
