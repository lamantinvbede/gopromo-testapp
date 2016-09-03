package ru.gopromo.testapp.other.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import ru.gopromo.testapp.presenters.BasePresenter;

public abstract class BaseFragment extends Fragment {

    protected abstract BasePresenter getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getPresenter() != null) {
            getPresenter().onSaveInstanceState(outState);
        }
    }
}
