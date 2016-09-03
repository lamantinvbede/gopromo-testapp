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

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<NewsItem> values = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final ImageView newsPhoto;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.news_title);
            newsPhoto = (ImageView) v.findViewById(R.id.news_photo);
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
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem newsItem = values.get(position);
        holder.title.setText(newsItem.getTitle());
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