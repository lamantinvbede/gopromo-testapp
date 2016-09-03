package ru.gopromo.testapp.models;


import java.util.List;

import rx.Observable;

public interface NewsSessionData {
    Observable<List<NewsItem>> getNews();
}
