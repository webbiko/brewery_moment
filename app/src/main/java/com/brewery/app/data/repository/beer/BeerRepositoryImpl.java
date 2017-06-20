package com.brewery.app.data.repository.beer;

import android.content.Context;

import com.brewery.app.BuildConfig;
import com.brewery.app.data.datasource.remote.RemoteFactory;
import com.brewery.app.data.datasource.remote.beer.BeerRemote;
import com.brewery.app.data.model.Beer;
import com.brewery.app.data.model.BreweryResult;
import com.brewery.app.data.repository.BaseRepository;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Query;

public class BeerRepositoryImpl extends BaseRepository implements BeerRepository {

    public BeerRepositoryImpl(Context context) {
        super(context);
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public Observable<Response<BreweryResult<Beer>>> loadBeers(@Query("styleId") int styleId) {
//        if (NetworkUtil.isNetworkAvailable(this.mContext.get())) {
        final BeerRemote beerRemote = RemoteFactory.create(BeerRemote.class);
        return beerRemote.loadBeers(BuildConfig.KEY, styleId);
//        }
//        return Observable.error(new IllegalAccessException("no network available"));
    }
}