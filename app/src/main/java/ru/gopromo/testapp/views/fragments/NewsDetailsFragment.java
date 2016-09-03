package ru.gopromo.testapp.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.gopromo.testapp.App;
import ru.gopromo.testapp.R;
import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.other.utils.NavigationController;
import ru.gopromo.testapp.presenters.BasePresenter;
import ru.gopromo.testapp.presenters.NewsDetailsPresenter;
import ru.gopromo.testapp.views.NewsDetailView;

public class NewsDetailsFragment extends BaseFragment implements NewsDetailView {

    public static final String NEWS_ITEM = "news_item";

    public static NewsDetailsFragment create(NewsItem item) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(NEWS_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    private Unbinder unbinder;

    @Inject
    NewsDetailsPresenter presenter;

    @BindView(R.id.details_title)
    TextView title;

    @BindView(R.id.details_description)
    TextView description;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.backdrop)
    ImageView backdrop;

    @BindView(R.id.details_date)
    TextView date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        presenter.setModel((NewsItem)getArguments().getSerializable(NEWS_ITEM));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.setView(this);
        presenter.onCreateView(savedInstanceState);
        return view;
    }

    @Override
    public void showProgress() {
        //TODO
    }

    @Override
    public void hideProgress() {
        //TODO
    }

    @Override
    public void showError(String errorMessage) {
        //TODO
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public NavigationController navigationController() {
        return getActivity() != null && getActivity() instanceof NavigationController
                ? (NavigationController) getActivity() : null;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void setTitle(String text) {
        title.setText(text);
    }

    @Override
    public void setDescription(String text) {
        description.setText(text);
    }

    @Override
    public void setImage(String url) {
        Glide.with(getContext()).load(url).into(backdrop);
    }

    @Override
    public void setCategory(String category) {
        collapsingToolbarLayout.setTitle(category);
    }

    @Override
    public void setDate(String dateString) {
        date.setText(dateString);
    }
}
