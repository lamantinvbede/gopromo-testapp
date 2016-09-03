package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.gopromo.testapp.App;
import ru.gopromo.testapp.R;
import ru.gopromo.testapp.presenters.NewsListPresenter;
import ru.gopromo.testapp.presenters.Presenter;
import ru.gopromo.testapp.views.adapters.BaseAdapter;
import ru.gopromo.testapp.views.adapters.NewsListAdapter;

public class NewsListFragment extends NewsBaseFragment {

    @Inject
    NewsListPresenter presenter;

    @BindView(R.id.news_list_rv)
    RecyclerView newsListRecycler;

    @BindView(R.id.news_list_pb)
    ProgressBar newsListPB;

    NewsListAdapter newsAdapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        newsAdapter = new NewsListAdapter(presenter);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected RecyclerView getRecycler() {
        return newsListRecycler;
    }

    @Override
    protected BaseAdapter getAdapter() {
        return newsAdapter;
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return newsListRecycler;
    }

    @Override
    public void showProgress() {
        newsListPB.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        newsListPB.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        //TODO
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
