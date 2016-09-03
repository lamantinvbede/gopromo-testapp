package ru.gopromo.testapp.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.einmalfel.earl.Enclosure;
import com.einmalfel.earl.Item;

import java.util.Date;
import java.util.List;

public class NewsItem implements Item {

    private final String link;
    private final Date publicationDate;
    private final String title;
    private final String description;
    private final String imageLink;
    private final String author;

    public NewsItem(Item item) {
        link = item.getLink();
        publicationDate = item.getPublicationDate();
        title = item.getTitle();
        description = item.getDescription();
        imageLink = item.getEnclosures().size() > 0 ?
                item.getEnclosures().get(0).getLink() : null;
        author = item.getAuthor();
    }

    @Nullable
    @Override
    public String getLink() {
        return link;
    }

    @Nullable
    @Override
    public Date getPublicationDate() {
        return publicationDate;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getDescription() {
        return description;
    }

    @Nullable
    @Override
    public String getImageLink() {
        return imageLink;
    }

    @Nullable
    @Override
    public String getAuthor() {
        return author;
    }

    @NonNull
    @Override
    public List<? extends Enclosure> getEnclosures() {
        return null;
    }

}
