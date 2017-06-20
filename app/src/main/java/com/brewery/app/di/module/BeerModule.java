package com.brewery.app.di.module;

import com.brewery.app.data.repository.beer.BeerRepository;
import com.brewery.app.data.repository.beer.BeerRepositoryImpl;
import com.brewery.app.infraestructure.BreweryApplication;
import com.brewery.app.presentation.beers.BeerPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class BeerModule {

    @Provides
    public BeerRepository getBeerRepository(BreweryApplication application) {
        return new BeerRepositoryImpl(application);
    }

    @Provides
    public BeerPresenter getBeerPresenter(BeerRepository beerRepository) {
        return new BeerPresenter(beerRepository);
    }
}