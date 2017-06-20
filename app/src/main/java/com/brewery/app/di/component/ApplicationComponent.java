package com.brewery.app.di.component;

import com.brewery.app.di.ApplicationContext;
import com.brewery.app.di.module.ApplicationModule;
import com.brewery.app.di.module.BeerModule;
import com.brewery.app.di.module.BreweryModule;
import com.brewery.app.infraestructure.BreweryApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        BeerModule.class,
        BreweryModule.class
})
public interface ApplicationComponent {

    void inject(BreweryApplication application);

    @ApplicationContext
    BreweryApplication getContext();

    BreweryApplication getApplication();

}