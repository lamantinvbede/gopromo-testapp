package ru.gopromo.testapp.models;


import java.util.List;

import rx.Observable;

public class ApiImpl implements Api {
    @Override
    public Observable<List<NewsItem>> getNews() {
        return null;
    }
}
