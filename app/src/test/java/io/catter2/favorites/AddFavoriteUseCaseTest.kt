package io.catter2.favorites

import org.junit.Assert
import org.junit.Test

class AddFavoriteUseCaseTest {
    @Test
    fun testAddTrue() {
        val stub = StubRepo()
        val uc = useCase(stub)

        val added = uc.addFavoriteUrl("url0")
        Assert.assertTrue(added)
    }

    @Test
    fun testAddFalse() {
        val stub = StubRepo()
        val uc = useCase(stub)

        stub.addModel = false

        val added = uc.addFavoriteUrl("url0")
        Assert.assertFalse(added)
    }

    private fun useCase(repo: FavoritesRepository) = AddFavoriteUseCase(repo)

    private class StubRepo : FavoritesRepository {
        internal val models = mutableSetOf<FavoriteModel>()
        internal var addModel = true

        override fun getFavorites(): List<FavoriteModel> {
            throw RuntimeException("Not implemented")
        }

        override fun addFavorite(model: FavoriteModel): List<FavoriteModel> {
            if (addModel) {
                models.add(model)
            }
            return models.toList()
        }

        override fun registerChangeListener(listener: FavoritesRepository.ChangeListener) {
            throw RuntimeException("Not implemented")
        }

        override fun clearChangeListener() {
            throw RuntimeException("Not implemented")
        }
    }
}
