package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.gopromo.testapp.presenters.Presenter;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onDestroyView() {
        if(getPresenter() != null) {
            getPresenter().onDestroyView();
        }
        super.onDestroyView();
    }

    protected abstract Presenter getPresenter();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
    }
}
