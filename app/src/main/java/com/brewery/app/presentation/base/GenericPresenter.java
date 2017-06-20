package com.brewery.app.presentation.base;

import android.support.annotation.NonNull;

/**
 * This interface will help presenter layer to avoid memory leak
 * @param <V> The view type
 */
public interface GenericPresenter<V> {
    /**
     * This method is responsible for injecting a view into presenter
     * and with this view reference it will be used to notify the UI
     * @param view
     */
    void attachView(@NonNull V view);

    /**
     * This method is responsible for unsubscribe events which will avoid memory leak
     */
    void detachView();
}