package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gopromo.testapp.App;
import ru.gopromo.testapp.R;
import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.presenters.BasePresenter;
import ru.gopromo.testapp.presenters.NewsListPresenter;
import ru.gopromo.testapp.views.NewsListView;
import ru.gopromo.testapp.views.adapters.NewsListAdapter;

public class NewsListFragment extends BaseFragment implements NewsListView {

    @Inject
    NewsListPresenter presenter;

    @BindView(R.id.news_list_rv)
    RecyclerView newsListRecycler;

    RecyclerView.LayoutManager layoutManager;

    NewsListAdapter newsAdapter = new NewsListAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        presenter.onCreateView(savedInstanceState, this);
        return view;
    }

    private void initViews() {
        newsListRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        newsListRecycler.setLayoutManager(layoutManager);
        newsListRecycler.setAdapter(newsAdapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showList(List<NewsItem> list) {
        newsAdapter.setValues(list);
    }

    @Override
    public void addList(List<NewsItem> list) {
        newsAdapter.addValues(list);
    }

    @Override
    public void showEmptyList() {
        newsAdapter.setValues(new ArrayList<>());
    }

    @Override
    public void showDetailsView(NewsItem item) {
        //TODO
    }

    @Override
    public RecyclerView getRecyclerView() {
        return newsListRecycler;
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
    public void showError() {
        //TODO
    }
}
