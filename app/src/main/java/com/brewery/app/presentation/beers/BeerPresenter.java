package com.brewery.app.presentation.beers;

import android.support.annotation.NonNull;

import com.brewery.app.data.datasource.remote.util.RemoteConstants.Application.StatusCode;
import com.brewery.app.data.model.Beer;
import com.brewery.app.data.model.BreweryResult;
import com.brewery.app.data.repository.beer.BeerRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BeerPresenter implements BeerContract.Presenter {

    private BeerContract.View mView;

    private BeerRepository mBeerRepository;

    private CompositeDisposable mCompositeSubscription;

    @Inject
    public BeerPresenter(final BeerRepository beerRepository) {
        this.mBeerRepository = beerRepository;
        mCompositeSubscription = new CompositeDisposable();
    }

    @Override
    public void attachView(@NonNull BeerContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.clear();
            this.mCompositeSubscription = null;
        }
    }

    @Override
    public void loadBeers() {
        if (this.mView != null) {
            this.mView.displayProgress();
            final DisposableObserver<Response<BreweryResult<Beer>>> disposal = mBeerRepository.loadBeers(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<BreweryResult<Beer>>>() {
                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull Response<BreweryResult<Beer>> breweryResultResponse) {
                            if (mView != null) {
                                mView.hideProgress();
                                if (breweryResultResponse == null || breweryResultResponse.code() != StatusCode.SUCCESS) {
                                    mView.handleBeerListLoadError();
                                } else {
                                    mView.displayBeers(breweryResultResponse.body().getData());
                                }
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            if (mView != null) {
                                mView.hideProgress();
                                mView.handleBeerListLoadError();
                            }
                        }

                        @Override
                        public void onComplete() {
                            // nothing to do
                        }
                    });
            mCompositeSubscription.add(disposal);
        }
    }

    @Override
    public void handleBeerListClick(Beer beer, final int position) {
        if (mView != null) {
            this.mView.displayBeerDetail(beer, position);
        }
    }
}