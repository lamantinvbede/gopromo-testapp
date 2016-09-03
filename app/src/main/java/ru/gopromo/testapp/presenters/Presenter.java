package ru.gopromo.testapp.presenters;


import android.os.Bundle;

import ru.gopromo.testapp.views.NewsView;

public abstract class Presenter {

    public abstract void onCreateView(Bundle savedInstanceState);

    public abstract void onDestroyView();

    public abstract void setView(NewsView view);

    public abstract void onSaveInstanceState(Bundle savedInstanceState);
}
