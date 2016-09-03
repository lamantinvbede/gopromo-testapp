package ru.gopromo.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.gopromo.testapp.presenters.NewsListPresenter;
import ru.gopromo.testapp.views.NewsListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsListPresenter listPresenter = new NewsListPresenter();
        listPresenter.onCreateView(savedInstanceState, new NewsListView());
    }
}
