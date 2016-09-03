package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.gopromo.testapp.presenters.Presenter;

public abstract class BaseFragment extends Fragment {

    protected abstract Presenter getPresenter();

    @Override
    public void onDestroyView() {
        if(getPresenter() != null) {
            getPresenter().onDestroyView();
        }
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
    }
}
