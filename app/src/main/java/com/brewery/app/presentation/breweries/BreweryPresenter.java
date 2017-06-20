package com.brewery.app.presentation.breweries;

import android.support.annotation.NonNull;

import com.brewery.app.data.datasource.remote.util.RemoteConstants;
import com.brewery.app.data.model.Brewery;
import com.brewery.app.data.model.BreweryResult;
import com.brewery.app.data.repository.brewery.BreweryRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BreweryPresenter implements BreweryContract.Presenter {

    private BreweryRepository mBreweryRepository;

    private BreweryContract.View mView;

    private CompositeDisposable mCompositeSubscription;

    @Inject
    public BreweryPresenter(final BreweryRepository breweryRepository) {
        this.mBreweryRepository = breweryRepository;
        this.mCompositeSubscription = new CompositeDisposable();
    }

    @Override
    public void attachView(@NonNull BreweryContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mBreweryRepository = null;
    }

    @Override
    public void loadBreweries() {
        if (this.mView != null) {
            this.mView.displayProgress();
            final DisposableObserver<Response<BreweryResult<Brewery>>> disposal = mBreweryRepository.loadBreweries("1997")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableObserver<Response<BreweryResult<Brewery>>>() {
                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull Response<BreweryResult<Brewery>> breweryResultResponse) {
                            if (mView != null) {
                                mView.hideProgress();
                                if (breweryResultResponse == null || breweryResultResponse.code() !=
                                        RemoteConstants.Application.StatusCode.SUCCESS) {
                                    mView.handleBreweryListLoadError();
                                } else {
                                    mView.displayBreweries(breweryResultResponse.body().getData());
                                }
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            if (mView != null) {
                                mView.hideProgress();
                                mView.handleBreweryListLoadError();
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
    public void handleBreweryListClick(Brewery brewery) {
        if (mView != null) {
            mView.displayBreweryDetails(brewery);
        }

    }
}