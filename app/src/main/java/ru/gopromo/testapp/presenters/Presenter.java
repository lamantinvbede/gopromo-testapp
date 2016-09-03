package ru.gopromo.testapp.presenters;


import android.os.Bundle;

public abstract class Presenter {

    public abstract void onDestroyView();

    public abstract void onSaveInstanceState(Bundle savedInstanceState);
}
