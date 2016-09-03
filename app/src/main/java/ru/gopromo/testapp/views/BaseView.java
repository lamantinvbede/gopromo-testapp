package ru.gopromo.testapp.views;


public interface BaseView {
    void showProgress();
    void hideProgress();
    void showError(String errorMessage);
}
