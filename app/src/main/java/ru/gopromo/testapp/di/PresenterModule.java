package ru.gopromo.testapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gopromo.testapp.models.NewsSessionData;
import ru.gopromo.testapp.models.NewsSessionDataImpl;

@Module
public class PresenterModule {
    @Provides
    @Singleton
    NewsSessionData provideSessionData() {
        return new NewsSessionDataImpl();
    }

}
