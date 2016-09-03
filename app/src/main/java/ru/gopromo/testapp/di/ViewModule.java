package ru.gopromo.testapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gopromo.testapp.presenters.NewsDetailsPresenter;
import ru.gopromo.testapp.presenters.NewsListPresenter;

@Module
public class ViewModule {
    //TODO remove a singleton later this is temporary workaround for an adapter
    @Singleton
    @Provides
    NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenter();
    }

    @Provides
    NewsDetailsPresenter provideNewsDetailsPresenter() {
        return new NewsDetailsPresenter();
    }
}
