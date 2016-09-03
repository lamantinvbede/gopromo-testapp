package ru.gopromo.testapp.models;


import java.util.List;

import rx.Observable;

public interface SessionData {
    Observable<List<NewsItem>> getNews();
}
