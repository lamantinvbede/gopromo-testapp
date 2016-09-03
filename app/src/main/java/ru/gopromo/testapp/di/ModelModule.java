package ru.gopromo.testapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.gopromo.testapp.models.Api;
import ru.gopromo.testapp.models.ApiProvider;
import ru.gopromo.testapp.other.Constants;

@Module
public class ModelModule {
    @Provides
    @Singleton
    Api provideApi() {
        return ApiProvider.getApi(Constants.BASE_URL);
    }
}
