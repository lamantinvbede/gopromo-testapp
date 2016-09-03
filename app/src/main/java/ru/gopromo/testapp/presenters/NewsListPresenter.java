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
            this.view.show(newsItems);
        }

    }

    private void loadData() {
        sessionData.getNews().subscribe(newsItems -> {
            for(Item item : newsItems) {
                Log.d(TAG, "item " + item.getTitle());
            }
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
