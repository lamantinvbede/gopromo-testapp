package ru.gopromo.testapp;


import android.app.Application;

import ru.gopromo.testapp.di.AppComponent;
import ru.gopromo.testapp.di.DaggerAppComponent;

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.create();
    }

}
