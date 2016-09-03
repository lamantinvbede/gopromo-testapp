package ru.gopromo.testapp.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.einmalfel.earl.Enclosure;
import com.einmalfel.earl.Item;

import java.util.Date;
import java.util.List;

public class NewsItem implements Item {

    private String link;
    private Date publicationDate;
    private String title;
    private String description;
    private String imageLink;
    private String author;
    private List<? extends Enclosure> encl;

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
        return encl;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEncl(List<? extends Enclosure> encl) {
        this.encl = encl;
    }
}
