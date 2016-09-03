package ru.gopromo.testapp.models;


import java.util.List;

import javax.inject.Inject;

import ru.gopromo.testapp.App;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SessionDataImpl implements SessionData {

    @Inject
    Api api;

    public SessionDataImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<List<NewsItem>> getNews() {
        return api.getNews()
                .map(new NewsMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    throwable.printStackTrace();
                    return Observable.empty();
                });
    }
}
