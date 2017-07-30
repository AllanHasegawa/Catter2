package io.catter2.favorites;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddFavoriteUseCaseTest {
    @Test
    public void testAddTrue() {
        StubRepo stub = new StubRepo();
        AddFavoriteUseCase uc = useCase(stub);

        boolean added = uc.addFavoriteUrl("url0");
        Assert.assertTrue(added);
    }

    @Test
    public void testAddFalse() {
        StubRepo stub = new StubRepo();
        AddFavoriteUseCase uc = useCase(stub);

        stub.addModel = false;

        boolean added = uc.addFavoriteUrl("url0");
        Assert.assertFalse(added);
    }

    private AddFavoriteUseCase useCase(FavoritesRepository repo) {
        return new AddFavoriteUseCase(repo);
    }

    private static class StubRepo implements FavoritesRepository {
        ArrayList<FavoriteModel> models;
        boolean addModel;

        public StubRepo() {
            addModel = true;
            models = new ArrayList<>();
        }

        @Override
        public List<FavoriteModel> getFavorites() {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public List<FavoriteModel> addFavorite(FavoriteModel model) {
            if (addModel) {
                models.add(model);
            }
            return models;
        }

        @Override
        public void registerChangeListener(ChangeListener listener) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public void clearChangeListener() {
            throw new RuntimeException("Not implemented");
        }
    }
}
