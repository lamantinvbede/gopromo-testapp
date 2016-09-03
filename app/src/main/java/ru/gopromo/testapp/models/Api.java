package ru.gopromo.testapp.models;


import java.util.List;

import rx.Observable;

public interface Api {
    Observable<List<NewsItem>> getNews();
}
