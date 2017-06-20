package com.brewery.app.infraestructure;

import android.app.Application;

import com.brewery.app.di.component.ApplicationComponent;
import com.brewery.app.di.component.DaggerApplicationComponent;
import com.brewery.app.di.module.ApplicationModule;
import com.brewery.app.di.module.BeerModule;

public class BreweryApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .beerModule(new BeerModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}