package ru.gopromo.testapp.presenters;

import java.util.List;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.PaginationListener;
import rx.Observable;

public class NewsDetailsPresenter extends BaseNewsPresenter {

    public void setNewsChunk(List<NewsItem> newsChunk) {
        this.newsItems = newsChunk;
    }

    @Override
    protected int getLimit() {
        return 1;
    }

    @Override
    protected PaginationListener<NewsItem> getPagingListener() {
        return offset -> {
            currentOffset = offset;
            return Observable.just(newsItems);
        };
    }
}
