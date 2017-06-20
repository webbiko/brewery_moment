package com.brewery.app.di.component;

import com.brewery.app.di.FragmentScoped;
import com.brewery.app.di.module.ApplicationModule;
import com.brewery.app.di.module.BeerModule;
import com.brewery.app.presentation.beers.BeerFragment;

import dagger.Component;

@FragmentScoped
@Component(modules = {ApplicationModule.class, BeerModule.class})
public interface BeerComponent {
    void inject(BeerFragment beerFragment);
}