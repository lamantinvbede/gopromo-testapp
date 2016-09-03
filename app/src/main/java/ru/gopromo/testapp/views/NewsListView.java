package ru.gopromo.testapp.views;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import ru.gopromo.testapp.models.NewsItem;

public interface NewsListView extends BaseView {

    void showList(List<NewsItem> list);

    void addList(List<NewsItem> list);

    void showEmptyList();

    RecyclerView getRecyclerView();
}
