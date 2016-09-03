package ru.gopromo.testapp.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.einmalfel.earl.Enclosure;
import com.einmalfel.earl.Item;
import com.einmalfel.earl.RSSCategory;
import com.einmalfel.earl.RSSItem;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ru.gopromo.testapp.other.utils.DateUtils;

public class NewsItem implements Serializable {

    @Nullable
    private final String publicationDate;
    @Nullable
    private final String title;
    @Nullable
    private final String description;
    @Nullable
    private final String imageLink;
    @NonNull
    private final String category;

    public NewsItem(RSSItem item) {

        publicationDate = DateUtils.getSimpleDateString(item.getPublicationDate());
        title = item.getTitle();
        description = item.getDescription() == null ? null : item.getDescription().trim();
        imageLink = item.getEnclosures().size() > 0 ?
                item.getEnclosures().get(0).getLink() : null;
        category = item.categories.isEmpty() ? "" : "#" + item.categories.get(0).value;
    }

    @Nullable
    public String getPublicationDate() {
        return publicationDate;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getImageLink() {
        return imageLink;
    }

    @NonNull
    public String getCategoryTitle() {
        return category;
    }
}
