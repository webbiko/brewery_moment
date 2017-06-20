package com.brewery.app.presentation.breweries;

import com.brewery.app.data.model.Brewery;
import com.brewery.app.presentation.base.GenericPresenter;
import com.brewery.app.presentation.base.GenericView;

import java.util.List;

public interface BreweryContract {

    interface View extends GenericView {

        void displayBreweries(List<Brewery> breweries);

        void hideProgress();

        void displayProgress();

        void handleBreweryListLoadError();

        void displayBreweryDetails(final Brewery brewery);
    }

    interface Presenter extends GenericPresenter<View> {

        void loadBreweries();

        void handleBreweryListClick(final Brewery brewery);
    }
}