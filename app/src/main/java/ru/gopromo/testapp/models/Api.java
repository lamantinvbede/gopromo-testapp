package ru.gopromo.testapp.models;


import com.einmalfel.earl.Item;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface Api {
    @GET("/rss/news")
    Observable<List<Item>> getNews();
}
