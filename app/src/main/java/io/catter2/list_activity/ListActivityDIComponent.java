package io.catter2.list_activity;

import javax.inject.Scope;

import dagger.Component;
import io.catter2.di.UserDIComponent;

@Component(modules = ListActivityDIModule.class,
        dependencies = UserDIComponent.class)
@ListActivityDIComponent.ListActivityScope
public abstract class ListActivityDIComponent {
    @Scope
    public @interface ListActivityScope {
    }

    public static void initializeAndInject(ListActivity activity) {
        DaggerListActivityDIComponent.builder()
                .userDIComponent(UserDIComponent.get())
                .build()
                .inject(activity);
    }

    public abstract void inject(ListActivity activity);
}
