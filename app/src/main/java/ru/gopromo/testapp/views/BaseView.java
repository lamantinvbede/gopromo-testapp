package ru.gopromo.testapp.views;


import ru.gopromo.testapp.other.utils.NavigationController;

public interface BaseView {
    void showProgress();
    void hideProgress();
    void showError(String errorMessage);
    NavigationController navigationController();
}
