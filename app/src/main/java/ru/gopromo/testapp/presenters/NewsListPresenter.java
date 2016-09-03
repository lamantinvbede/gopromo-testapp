package ru.gopromo.testapp.presenters;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.models.SessionData;
import ru.gopromo.testapp.other.utils.PaginationUtils;
import ru.gopromo.testapp.views.NewsListView;
import ru.gopromo.testapp.views.fragments.NewsDetailsFragment;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class NewsListPresenter extends BasePresenter {

    private static final String TAG = "NewsListPresenter";
    public static final int LIMIT = 10;
    private static final String NEWS_ITEMS = "news_items";
    private static final String OFFSET = "offset";

    List<NewsItem> newsItems;
    private int currentOffset = 0;
    private Subscription subscription;

    private NewsListView view;
    private SessionData sessionData;

    public void setModel(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    public void setView(NewsListView view) {
        this.view = view;
    }

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

    private void loadData() {
        subscription = PaginationUtils.paging(view.getRecyclerView(),
                offset -> {
                    currentOffset = offset;
                    Log.d(TAG, "CURRENT_OFFSET : " + currentOffset);
                    if(currentOffset == 0) {
                        return sessionData.getNews();
                    } else {
                        return Observable.just(this.newsItems);
                    }
                }, LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItems -> {
                    this.newsItems = newsItems;
                    if(this.newsItems.isEmpty()) {
                        view.showEmptyList();
                        view.hideProgress();
                        return;
                    }
                    int endSubList = newsItems.size();
                    if(endSubList - currentOffset > LIMIT) {
                        endSubList = currentOffset + LIMIT;
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
        if(endSubList - currentOffset > LIMIT) {
            endSubList = currentOffset + LIMIT;
        }
        List<NewsItem> result =
                new ArrayList<>(this.newsItems.subList(0, endSubList));
        view.showList(result);
        loadData();
        view.hideProgress();
    }

    public void onNewsItemClick(NewsItem newsItem) {
        view.navigationController().replaceFragment(NewsDetailsFragment.create(newsItem), true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(NEWS_ITEMS, (ArrayList<NewsItem>)newsItems);
        savedInstanceState.putInt(OFFSET, currentOffset);
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroyView() {
        if(subscription != null)
            subscription.unsubscribe();
    }
}
