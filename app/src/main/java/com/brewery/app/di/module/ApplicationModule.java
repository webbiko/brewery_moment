package com.brewery.app.di.module;

import com.brewery.app.di.ApplicationContext;
import com.brewery.app.infraestructure.BreweryApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final BreweryApplication mApplication;

    public ApplicationModule(final BreweryApplication breweryApplication) {
        this.mApplication = breweryApplication;
    }

    @Provides
    @ApplicationContext
    BreweryApplication getContext() {
        return mApplication;
    }

    @Provides
    BreweryApplication getApplication() {
        return mApplication;
    }
}