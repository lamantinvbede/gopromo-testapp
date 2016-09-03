package ru.gopromo.testapp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.gopromo.testapp.models.NewsSessionDataImpl;
import ru.gopromo.testapp.presenters.BaseNewsPresenter;
import ru.gopromo.testapp.views.NewsView;
import ru.gopromo.testapp.views.adapters.NewsListAdapter;
import ru.gopromo.testapp.views.fragments.NewsDetailsFragment;
import ru.gopromo.testapp.views.fragments.NewsListFragment;

@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(NewsSessionDataImpl sessionData);

    void inject(NewsListFragment newsListFragment);

    void inject(NewsListAdapter newsListAdapter);

    void inject(NewsDetailsFragment newsDetailsFragment);

    void inject(NewsView baseNewsView);

    void inject(BaseNewsPresenter baseNewsPresenter);
}
