package io.catter2.list_activity;

import io.catter2.di.UserDIComponent;

public class ListActivityDIComponent {
    private ListActivityDIModule module;

    public ListActivityDIComponent() {
        this.module = new ListActivityDIModule(UserDIComponent.get());
    }

    public void inject(ListActivity activity) {
        activity.injectAddFavoriteUseCase(module.provideAddFavoriteUseCase());
        activity.injectFetchCatImagesUseCase(module.provideFetchCatImagesUseCase());
    }
}
