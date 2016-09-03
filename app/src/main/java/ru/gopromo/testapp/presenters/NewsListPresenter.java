package ru.gopromo.testapp.presenters;

import java.util.ArrayList;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.PaginationListener;
import ru.gopromo.testapp.views.fragments.NewsDetailsFragment;
import rx.Observable;

public class NewsListPresenter extends BaseNewsPresenter {

    public void onNewsItemClick(int startPosition) {
        ArrayList<NewsItem> newsChunk = new ArrayList<>(newsItems.subList(startPosition, newsItems.size()));
        view.navigationController().replaceFragment(NewsDetailsFragment.create(newsChunk), true);
    }

    @Override
    protected int getLimit() {
        return 10;
    }

    @Override
    protected PaginationListener<NewsItem> getPagingListener() {
        return offset -> {
            currentOffset = offset;
            if(currentOffset == 0) {
                return sessionData.getNews();
            } else {
                return Observable.just(this.newsItems);
            }
        };
    }

    public void refresh() {
        view.startRefreshing();
        currentOffset = 0;
        subscription.unsubscribe();
        loadData(true);
        view.getRecyclerView().postDelayed(() -> view.finishRefreshing(), 200);
    }
}
