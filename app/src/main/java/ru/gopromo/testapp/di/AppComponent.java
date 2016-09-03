package ru.gopromo.testapp.di;

import dagger.Component;

@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {
}
