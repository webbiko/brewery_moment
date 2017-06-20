package com.brewery.app.data.repository.brewery;

import com.brewery.app.data.model.Brewery;
import com.brewery.app.data.model.BreweryResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Query;

public interface BreweryRepository {

    Observable<Response<BreweryResult<Brewery>>> loadBreweries(@Query("established") String established);

}