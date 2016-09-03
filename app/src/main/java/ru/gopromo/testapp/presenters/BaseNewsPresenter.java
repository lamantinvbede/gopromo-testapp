package ru.gopromo.testapp.presenters;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.gopromo.testapp.App;
import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.models.NewsSessionData;
import ru.gopromo.testapp.other.utils.PaginationListener;
import ru.gopromo.testapp.other.utils.PaginationUtils;
import ru.gopromo.testapp.views.NewsView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public abstract class BaseNewsPresenter extends Presenter {

    @Inject
    protected NewsSessionData sessionData;
    protected NewsView view;

    public BaseNewsPresenter() {
        App.getComponent().inject(this);
    }

    private static final String NEWS_ITEMS = "news_items";
    private static final String OFFSET = "offset";
    protected List<NewsItem> newsItems;

    protected int currentOffset = 0;
    private Subscription subscription;
    public void onCreateView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            loadSavedData(savedInstanceState);
        }
        view.showProgress();
        if (newsItems == null || newsItems.isEmpty()) {
            loadData();
        } else {
            showSavedData();
        }
    }

    public void setView(NewsView view) {
        this.view = view;
    }

    private void loadData() {
        subscription =  PaginationUtils.paging(view.getRecyclerView(), getPagingListener(),
                getLimit())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItems -> {
                    this.newsItems = newsItems;
                    if(this.newsItems.isEmpty()) {
                        view.showEmptyList();
                        view.hideProgress();
                        return;
                    }
                    int endSubList = newsItems.size();
                    if(endSubList - currentOffset > getLimit()) {
                        endSubList = currentOffset + getLimit();
                    }
                    List<NewsItem> result =
                            new ArrayList<>(this.newsItems.subList(currentOffset, endSubList));
                    if(currentOffset == 0) {
                        view.showList(result);
                    } else {
                        view.addList(result);
                    }
                    view.hideProgress();
                });
    }

    private void loadSavedData(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey(NEWS_ITEMS))
            newsItems = (List<NewsItem>) savedInstanceState.getSerializable(NEWS_ITEMS);
        currentOffset = savedInstanceState.getInt(OFFSET, 0);
    }

    private void showSavedData() {
        int endSubList = newsItems.size();
        if(endSubList - currentOffset > getLimit()) {
            endSubList = currentOffset + getLimit();
        }
        List<NewsItem> result =
                new ArrayList<>(this.newsItems.subList(0, endSubList));
        view.showList(result);
        loadData();
        view.hideProgress();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(NEWS_ITEMS, (ArrayList<NewsItem>)newsItems);
        savedInstanceState.putInt(OFFSET, currentOffset);
    }

    @Override
    public void onDestroyView() {
        if(subscription != null)
            subscription.unsubscribe();
    }

    protected abstract int getLimit();
    protected abstract PaginationListener<NewsItem> getPagingListener();
}
