package ru.gopromo.testapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gopromo.testapp.models.SessionData;
import ru.gopromo.testapp.models.SessionDataImpl;

@Module
public class PresenterModule {
    @Provides
    @Singleton
    SessionData provideSessionData() {
        return new SessionDataImpl();
    }
}
