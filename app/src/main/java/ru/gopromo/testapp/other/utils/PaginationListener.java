package ru.gopromo.testapp.other.utils;

import java.util.List;

import rx.Observable;

public interface PaginationListener<T> {
    Observable<List<T>> onNextPage(int offset);
}

