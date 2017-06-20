package com.brewery.app.data.datasource.remote.retrofit;

import com.brewery.app.data.datasource.remote.interceptors.BasicHeadersInterceptor;
import com.brewery.app.data.datasource.remote.util.RemoteConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is responsible for creating an instance of {@link Retrofit} which
 * will be used to communicate with server
 */
public class DefaultRetrofit {
    private static Retrofit sInstance;

    private DefaultRetrofit() {
        // an instance of default retrofit must be gotten from get method
    }

    public static Retrofit get() {
        if (sInstance == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                    .addInterceptor(loggingInterceptor);

            builder.addInterceptor(new BasicHeadersInterceptor());

            // Make singleton
            sInstance = new Retrofit.Builder()
                    .baseUrl(RemoteConstants.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(AndroidSchedulers.mainThread()))
                    .client(builder.build())
                    .build();
        }
        return sInstance;
    }
}