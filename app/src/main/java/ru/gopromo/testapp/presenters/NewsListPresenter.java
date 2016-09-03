package ru.gopromo.testapp.presenters;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.PaginationUtils;
import ru.gopromo.testapp.views.NewsListView;
import rx.Observable;
import rx.Subscription;

public class NewsListPresenter extends BasePresenter {

    private static final String TAG = "NewsListPresenter";
    public static final int LIMIT = 10;

    List<NewsItem> newsItems;
    private NewsListView view;
    private int currentOffset = 0;
    private Subscription subscription;

    public void onCreateView(Bundle savedInstanceState, NewsListView view) {
        if (savedInstanceState != null) {
            //TODO read from bundle
        }
        this.view = view;
        if (newsItems == null) {
            loadData();
        } else {
            this.view.showList(newsItems);
        }

    }

    private void loadData() {
        view.showProgress();
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
                .subscribe(newsItems -> {
                    this.newsItems = newsItems;
                    if(this.newsItems.isEmpty()) {
                        view.showEmptyList();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //TODO save instance
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
