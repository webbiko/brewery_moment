package com.brewery.app.presentation.beers;

import com.brewery.app.data.model.Beer;
import com.brewery.app.presentation.base.GenericPresenter;
import com.brewery.app.presentation.base.GenericView;

import java.util.List;

public interface BeerContract {

    interface View extends GenericView {

        void hideProgress();

        void displayProgress();

        void displayBeers(final List<Beer> beers);

        void displayBeerDetail(final Beer beer, final int position);

        void handleBeerListLoadError();

    }

    interface Presenter extends GenericPresenter<BeerContract.View> {
        void loadBeers();

        void handleBeerListClick(final Beer beer, final int position);
    }

}