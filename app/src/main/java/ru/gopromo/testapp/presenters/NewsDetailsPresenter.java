package ru.gopromo.testapp.presenters;

import android.os.Bundle;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.views.NewsDetailView;

public class NewsDetailsPresenter extends BasePresenter {

    private NewsItem newsItem;
    private NewsDetailView newsDetailView;

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroyView() {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //TODO
    }

    public void onCreateView(Bundle savedInstanceState) {
        newsDetailView.setImage(newsItem.getImageLink());
        newsDetailView.setTitle(newsItem.getTitle());
        newsDetailView.setDescription(newsItem.getDescription());
        newsDetailView.setCategory(newsItem.getCategoryTitle());
        newsDetailView.setDate(newsItem.getPublicationDate());
    }

    public void setView(NewsDetailView newsDetailView) {
        this.newsDetailView = newsDetailView;
    }

    public void setModel(NewsItem newsItem) {
        this.newsItem = newsItem;
    }
}
