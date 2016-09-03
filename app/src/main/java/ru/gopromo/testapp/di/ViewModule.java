package ru.gopromo.testapp.di;

import dagger.Module;
import dagger.Provides;
import ru.gopromo.testapp.presenters.NewsListPresenter;

@Module
public class ViewModule {
    @Provides
    NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenter();
    }
}
