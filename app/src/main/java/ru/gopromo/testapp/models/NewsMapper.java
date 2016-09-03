package ru.gopromo.testapp.models;


import com.einmalfel.earl.RSSItem;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class NewsMapper implements Func1<List<RSSItem>, List<NewsItem>> {

    @Override
    public List<NewsItem> call(List<RSSItem> items) {
        if (items == null) {
            return null;
        }
        return Observable.from(items)
                .map(NewsItem::new)
                .toList()
                .toBlocking()
                .first();
    }
}
