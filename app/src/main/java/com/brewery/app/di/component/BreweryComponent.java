package com.brewery.app.di.component;

import com.brewery.app.di.FragmentScoped;
import com.brewery.app.di.module.ApplicationModule;
import com.brewery.app.di.module.BreweryModule;
import com.brewery.app.presentation.breweries.BreweryListFragment;

import dagger.Component;

@FragmentScoped
@Component(modules = {ApplicationModule.class, BreweryModule.class})
public interface BreweryComponent {
    void inject(BreweryListFragment breweryFragment);
}