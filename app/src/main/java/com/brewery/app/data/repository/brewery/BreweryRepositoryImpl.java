package com.brewery.app.data.repository.brewery;

import android.content.Context;

import com.brewery.app.BuildConfig;
import com.brewery.app.data.datasource.remote.RemoteFactory;
import com.brewery.app.data.datasource.remote.brewery.BreweryRemote;
import com.brewery.app.data.model.Brewery;
import com.brewery.app.data.model.BreweryResult;
import com.brewery.app.data.repository.BaseRepository;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Query;

public class BreweryRepositoryImpl extends BaseRepository implements BreweryRepository {

    public BreweryRepositoryImpl(Context context) {
        super(context);
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public Observable<Response<BreweryResult<Brewery>>> loadBreweries(@Query("established") String established) {
        final BreweryRemote beerRemote = RemoteFactory.create(BreweryRemote.class);
        return beerRemote.loadBreweries(BuildConfig.KEY, established);
    }
}