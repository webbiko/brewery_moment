package com.brewery.app.data.datasource.remote.interceptors;

import com.brewery.app.data.datasource.remote.util.RemoteConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class provide a basic authentication class which adds header information common to all request
 */
public class BasicHeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", RemoteConstants.SUPPORTED_CONTENT_TYPE)
                .build();

        return chain.proceed(request);
    }
}