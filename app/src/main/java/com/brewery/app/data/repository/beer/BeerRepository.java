package com.brewery.app.data.repository.beer;

import com.brewery.app.data.model.Beer;
import com.brewery.app.data.model.BreweryResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Query;

public interface BeerRepository {

    Observable<Response<BreweryResult<Beer>>> loadBeers(@Query("styleId") int styleId);

}