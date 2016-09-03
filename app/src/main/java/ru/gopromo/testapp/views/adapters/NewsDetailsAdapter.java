package ru.gopromo.testapp.views.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.gopromo.testapp.R;
import ru.gopromo.testapp.models.NewsItem;
import ru.gopromo.testapp.presenters.NewsListPresenter;

public class NewsDetailsAdapter extends RecyclerView.Adapter<NewsDetailsAdapter.ViewHolder> {

    private List<NewsItem> values = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;
        public final TextView date;
        public final TextView category;
        public final TextView description;
        public final ImageView newsPhoto;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.news_title);
            newsPhoto = (ImageView) v.findViewById(R.id.news_photo);
            category = (TextView) v.findViewById(R.id.news_category);
            date = (TextView) v.findViewById(R.id.news_date);
            description = (TextView) v.findViewById(R.id.news_description);
        }
    }

    public void setValues(List<NewsItem> newsItems) {
        values = newsItems;
        notifyDataSetChanged();
    }

    public void addValues(List<NewsItem> additional) {
        int currentSize = values.size();
        values.addAll(additional);
        notifyItemRangeInserted(currentSize, additional.size());
    }

    @Override
    public NewsDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_details_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsItem newsItem = values.get(position);
        setTitle(holder, newsItem);
        setPhoto(holder, newsItem);
        setCategory(holder, newsItem);
        setDate(holder, newsItem);
        setDescription(holder, newsItem);
    }

    private void setDate(ViewHolder holder, NewsItem newsItem) {
        holder.date.setText(newsItem.getPublicationDate());
    }

    private void setCategory(ViewHolder holder, NewsItem newsItem) {
        holder.category.setText(newsItem.getCategoryTitle());
    }

    private void setTitle(ViewHolder holder, NewsItem newsItem) {
        holder.title.setText(newsItem.getTitle());
    }

    private void setDescription(ViewHolder holder, NewsItem newsItem) {
        holder.description.setText(newsItem.getDescription());
    }

    private void setPhoto(ViewHolder holder, NewsItem newsItem) {
        if(newsItem.getImageLink() != null) {
            holder.newsPhoto.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(newsItem.getImageLink())
                    .into(holder.newsPhoto);
        } else {
            holder.newsPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}