package ru.gopromo.testapp.views.adapters;


import java.util.List;

import ru.gopromo.testapp.models.NewsItem;

public interface BaseAdapter {

    void setValues(List<NewsItem> newsItems);

    void addValues(List<NewsItem> additional);
}
