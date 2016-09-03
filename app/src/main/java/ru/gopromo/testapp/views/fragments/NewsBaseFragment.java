package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.NavigationController;
import ru.gopromo.testapp.views.NewsView;
import ru.gopromo.testapp.views.adapters.BaseAdapter;

public abstract class NewsBaseFragment extends BaseFragment implements NewsView {

    protected RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        getPresenter().setView(this);
        getPresenter().onCreateView(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void initViews() {
        getRecycler().setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        getRecycler().setLayoutManager(layoutManager);
        getRecycler().setAdapter((RecyclerView.Adapter) getAdapter());
    }

    @Override
    public void showList(List<NewsItem> list) {
        getRecycler().setAlpha(0f);
        getRecycler().setVisibility(View.VISIBLE);
        getRecycler().animate().alpha(1f);
        getAdapter().setValues(list);
    }

    @Override
    public void addList(List<NewsItem> list) {
        getAdapter().addValues(list);
    }

    @Override
    public void showEmptyList() {
        getAdapter().setValues(new ArrayList<>());
        //TODO add showing no news logic textview
    }

    protected AppCompatActivity getCompatActivity() {
        return (AppCompatActivity)getActivity();
    }

    protected abstract RecyclerView getRecycler();

    protected abstract BaseAdapter getAdapter();

    @Override
    public NavigationController navigationController() {
        return getActivity() != null && getActivity() instanceof NavigationController
                ? (NavigationController) getActivity() : null;
    }
}
