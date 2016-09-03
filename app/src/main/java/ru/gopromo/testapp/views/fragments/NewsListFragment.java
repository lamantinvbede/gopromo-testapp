package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.gopromo.testapp.App;
import ru.gopromo.testapp.MainActivity;
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

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    NewsListAdapter newsAdapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setHasOptionsMenu(true);
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
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                presenter.refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void startRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void finishRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
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
