package ru.gopromo.testapp.other.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ru.gopromo.testapp.App;
import ru.gopromo.testapp.presenters.BasePresenter;
import ru.gopromo.testapp.presenters.NewsListPresenter;

public class NewsListFragment extends BaseFragment {

    @Inject
    NewsListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
