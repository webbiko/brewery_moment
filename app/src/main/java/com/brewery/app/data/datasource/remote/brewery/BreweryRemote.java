package com.brewery.app.data.datasource.remote.brewery;

import com.brewery.app.data.model.Brewery;
import com.brewery.app.data.model.BreweryResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BreweryRemote {

    @GET("breweries")
    Observable<Response<BreweryResult<Brewery>>> loadBreweries(@Query("key") final String key,
                                                               @Query("established") String established);

}