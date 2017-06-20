package com.brewery.app.data.datasource.remote.beer;

import com.brewery.app.data.model.Beer;
import com.brewery.app.data.model.BreweryResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BeerRemote {

    @GET("beers")
    Observable<Response<BreweryResult<Beer>>> loadBeers(@Query("key") final String key,
                                                        @Query("styleId") int styleId);

}