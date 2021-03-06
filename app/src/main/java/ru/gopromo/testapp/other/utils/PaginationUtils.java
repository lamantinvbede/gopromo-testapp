package ru.gopromo.testapp.other.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class PaginationUtils {

    private static final int EMPTY_LIST_ITEMS_COUNT = 0;
    private static final int DEFAULT_LIMIT = 50;
    private static final int MAX_ATTEMPTS_TO_RETRY_LOADING = 3;
    private static final String TAG = "PagingUtils";

    public static <T> Observable<List<T>> paging(RecyclerView recyclerView, PaginationListener<T> pagingListener) {
        return paging(recyclerView, pagingListener, DEFAULT_LIMIT, EMPTY_LIST_ITEMS_COUNT, MAX_ATTEMPTS_TO_RETRY_LOADING, false);
    }

    public static <T> Observable<List<T>> paging(RecyclerView recyclerView, PaginationListener<T> pagingListener, boolean forceRefesh) {
        return paging(recyclerView, pagingListener, DEFAULT_LIMIT, EMPTY_LIST_ITEMS_COUNT, MAX_ATTEMPTS_TO_RETRY_LOADING, forceRefesh);
    }

    public static <T> Observable<List<T>> paging(RecyclerView recyclerView, PaginationListener<T> pagingListener, int limit, boolean forceRefresh) {
        return paging(recyclerView, pagingListener, limit, EMPTY_LIST_ITEMS_COUNT, MAX_ATTEMPTS_TO_RETRY_LOADING, forceRefresh);
    }

    public static <T> Observable<List<T>> paging(RecyclerView recyclerView, PaginationListener<T> pagingListener, int limit, int emptyListCount, boolean forceRefresh) {
        return paging(recyclerView, pagingListener, limit, emptyListCount, MAX_ATTEMPTS_TO_RETRY_LOADING, forceRefresh);
    }

    public static <T> Observable<List<T>> paging(RecyclerView recyclerView, PaginationListener<T> pagingListener, int limit, int emptyListCount, int retryCount, boolean forceRefresh) {
        if (recyclerView == null) {
            throw new PagingException("null recyclerView");
        }
        if (recyclerView.getAdapter() == null) {
            throw new PagingException("null recyclerView adapter");
        }
        if (limit <= 0) {
            throw new PagingException("limit must be greater then 0");
        }
        if (emptyListCount < 0) {
            throw new PagingException("emptyListCount must be not less then 0");
        }
        if (retryCount < 0) {
            throw new PagingException("retryCount must be not less then 0");
        }

        int startNumberOfRetryAttempt = 0;
        return getScrollObservable(recyclerView, limit, emptyListCount, forceRefresh)
                .subscribeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .observeOn(Schedulers.io())
                .switchMap(offset -> getPagingObservable(pagingListener, pagingListener.onNextPage(offset), startNumberOfRetryAttempt, offset, retryCount));
    }

    private static Observable<Integer> getScrollObservable(RecyclerView recyclerView, int limit, int emptyListCount, boolean forceRefresh) {
        return Observable.create(subscriber -> {
            final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!subscriber.isUnsubscribed()) {
                        int position = getLastVisibleItemPosition(recyclerView);
                        int updatePosition = recyclerView.getAdapter().getItemCount() - 1 - (limit / 2);
                        if (position >= updatePosition) {
                            subscriber.onNext(recyclerView.getAdapter().getItemCount());
                        }
                    }
                }
            };
            recyclerView.addOnScrollListener(sl);
            subscriber.add(Subscriptions.create(() -> recyclerView.removeOnScrollListener(sl)));
            if (recyclerView.getAdapter().getItemCount() == emptyListCount ||
                    !isEnoughContentToScroll(recyclerView)) {
                subscriber.onNext(recyclerView.getAdapter().getItemCount());
            } else if(forceRefresh) {
                subscriber.onNext(0);
            }
        });
    }

    private static boolean isEnoughContentToScroll(RecyclerView recyclerView) {
        //I know, that will work normally only for linearLayoutManager
        Class recyclerViewLMClass = recyclerView.getLayoutManager().getClass();
        if (recyclerViewLMClass == LinearLayoutManager.class
                || LinearLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            boolean cantScrollDownwards = linearLayoutManager
                    .findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1;
            boolean cantScrollUpwards = linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0;
            return !(cantScrollDownwards && cantScrollUpwards);
        }

        return true;
    }

    private static int getLastVisibleItemPosition(RecyclerView recyclerView) {
        Class recyclerViewLMClass = recyclerView.getLayoutManager().getClass();
        if (recyclerViewLMClass == LinearLayoutManager.class || LinearLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            return linearLayoutManager.findLastVisibleItemPosition();
        } else if (recyclerViewLMClass == StaggeredGridLayoutManager.class || StaggeredGridLayoutManager.class.isAssignableFrom(recyclerViewLMClass)) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager)recyclerView.getLayoutManager();
            int[] into = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
            List<Integer> intoList = new ArrayList<>();
            for (int i : into) {
                intoList.add(i);
            }
            return Collections.max(intoList);
        }
        throw new PagingException("Unknown LayoutManager class: " + recyclerViewLMClass.toString());
    }

    private static <T> Observable<List<T>> getPagingObservable(PaginationListener<T> listener, Observable<List<T>> observable, int numberOfAttemptToRetry, int offset, int retryCount) {
        return observable.onErrorResumeNext(throwable -> {
            // retry to load new data portion if error occurred
            if (numberOfAttemptToRetry < retryCount) {
                int attemptToRetryInc = numberOfAttemptToRetry + 1;
                return getPagingObservable(listener, listener.onNextPage(offset), attemptToRetryInc, offset, retryCount);
            } else {
                return Observable.empty();
            }
        });
    }

}
