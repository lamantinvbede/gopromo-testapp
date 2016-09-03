package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.gopromo.testapp.App;
import ru.gopromo.testapp.R;
import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.NavigationController;
import ru.gopromo.testapp.presenters.BasePresenter;
import ru.gopromo.testapp.presenters.NewsDetailsPresenter;
import ru.gopromo.testapp.views.NewsDetailView;

public class NewsDetailsFragment extends BaseFragment implements NewsDetailView {

    public static NewsDetailsFragment create(NewsItem item) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        fragment.item = item;
        return fragment;
    }

    private NewsItem item;

    private Unbinder unbinder;

    @Inject
    NewsDetailsPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        presenter.onCreateView(savedInstanceState, this);
        return view;
    }

    private void initViews() {

    }

    @Override
    public void showProgress() {
        //TODO
    }

    @Override
    public void hideProgress() {
        //TODO
    }

    @Override
    public void showError(String errorMessage) {
        //TODO
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public NavigationController navigationController() {
        return getActivity() != null && getActivity() instanceof NavigationController
                ? (NavigationController) getActivity() : null;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
