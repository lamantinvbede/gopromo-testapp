package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.gopromo.testapp.App;
import ru.gopromo.testapp.R;
import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.NavigationController;
import ru.gopromo.testapp.presenters.NewsDetailsPresenter;
import ru.gopromo.testapp.presenters.Presenter;
import ru.gopromo.testapp.views.NewsView;
import ru.gopromo.testapp.views.adapters.NewsDetailsAdapter;

public class NewsDetailsFragment extends BaseFragment implements NewsView {

    private static final String NEWS_ITEMS = "news_items";

    public static NewsDetailsFragment create(ArrayList<NewsItem> items) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(NEWS_ITEMS, items);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    NewsDetailsPresenter presenter;

    @BindView(R.id.news_list_rv)
    RecyclerView newsListRecycler;

    @BindView(R.id.news_list_pb)
    ProgressBar newsListPB;

    RecyclerView.LayoutManager layoutManager;

    NewsDetailsAdapter newsAdapter = new NewsDetailsAdapter();
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        presenter.setNewsChunk((List<NewsItem>) getArguments().getSerializable(NEWS_ITEMS));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_details_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews();
        presenter.setView(this);
        presenter.onCreateView(savedInstanceState);
        return view;
    }

    private void initViews() {
        newsListRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        newsListRecycler.setLayoutManager(layoutManager);
        newsListRecycler.setAdapter(newsAdapter);
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showList(List<NewsItem> list) {
        newsListRecycler.setAlpha(0f);
        newsListRecycler.setVisibility(View.VISIBLE);
        newsListRecycler.animate().alpha(1f);
        newsAdapter.setValues(list);
    }

    @Override
    public void addList(List<NewsItem> list) {
        newsAdapter.addValues(list);
    }

    @Override
    public void showEmptyList() {
        newsAdapter.setValues(new ArrayList<>());
        //TODO add showing no news logic textview
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
