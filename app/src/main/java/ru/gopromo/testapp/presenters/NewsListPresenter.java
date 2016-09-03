package ru.gopromo.testapp.presenters;

import android.os.Bundle;
import android.util.Log;

import com.einmalfel.earl.Item;

import java.util.List;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.views.NewsListView;

public class NewsListPresenter extends BasePresenter {

    private static final String TAG = "NewsListPresenter";

    List<NewsItem> newsItems;
    private NewsListView view;

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
        sessionData.getNews().subscribe(newsItems -> {
            if(newsItems.size() > 0) {
                view.showList(newsItems);
            } else {
                view.showEmptyList();
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
}
