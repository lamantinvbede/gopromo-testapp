package ru.gopromo.testapp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.gopromo.testapp.models.SessionDataImpl;
import ru.gopromo.testapp.presenters.BasePresenter;
import ru.gopromo.testapp.views.fragments.NewsListFragment;

@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {
    void inject(SessionDataImpl sessionData);
    void inject(BasePresenter basePresenter);
    void inject(NewsListFragment newsListFragment);
}
