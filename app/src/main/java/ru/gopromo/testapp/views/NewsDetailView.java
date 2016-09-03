package ru.gopromo.testapp.views;

public interface NewsDetailView extends BaseView {

    void setTitle(String text);

    void setDescription(String description);

    void setImage(String url);

    void setCategory(String category);

    void setDate(String dateString);
}
