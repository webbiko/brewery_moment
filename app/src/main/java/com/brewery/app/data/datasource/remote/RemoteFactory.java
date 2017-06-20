package com.brewery.app.data.datasource.remote;

import com.brewery.app.data.datasource.remote.retrofit.DefaultRetrofit;

public abstract class RemoteFactory {

    private RemoteFactory() {
        // private constructor
    }

    public static <T> T create(Class<T> zClass) {
        return DefaultRetrofit.get().create(zClass);
    }
}