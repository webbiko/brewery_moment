package com.brewery.app.di.module;

import com.brewery.app.data.repository.brewery.BreweryRepository;
import com.brewery.app.data.repository.brewery.BreweryRepositoryImpl;
import com.brewery.app.infraestructure.BreweryApplication;
import com.brewery.app.presentation.breweries.BreweryPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class BreweryModule {

    @Provides
    public BreweryRepository getBreweryRepository(BreweryApplication application) {
        return new BreweryRepositoryImpl(application);
    }

    @Provides
    public BreweryPresenter getBreweryPresenter(BreweryRepository breweryRepository) {
        return new BreweryPresenter(breweryRepository);
    }

}