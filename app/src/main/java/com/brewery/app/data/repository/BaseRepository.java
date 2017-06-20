package com.brewery.app.data.repository;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * This class is responsible to provide a contract for all repositories
 * to have the context associated in it for datasource purpose
 */
public abstract class BaseRepository {

    protected WeakReference<Context> mContext;

    public BaseRepository(final Context context) {
        this.mContext = new WeakReference<>(context);
    }
}