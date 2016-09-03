package ru.gopromo.testapp.presenters;


import android.os.Bundle;

import javax.inject.Inject;

import ru.gopromo.testapp.App;
import ru.gopromo.testapp.models.SessionData;

public abstract class BasePresenter {

    @Inject
    protected SessionData sessionData;

    public BasePresenter() {
        App.getComponent().inject(this);
    }

    public abstract void onStop();

    public abstract void onSaveInstanceState(Bundle savedInstanceState);
}
