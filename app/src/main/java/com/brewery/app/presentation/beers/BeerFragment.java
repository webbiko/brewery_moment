package com.brewery.app.presentation.beers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brewery.app.R;
import com.brewery.app.data.model.Beer;
import com.brewery.app.di.component.BeerComponent;
import com.brewery.app.di.component.DaggerBeerComponent;
import com.brewery.app.di.module.ApplicationModule;
import com.brewery.app.infraestructure.BreweryApplication;
import com.brewery.app.presentation.base.BaseFragment;
import com.brewery.app.presentation.beers.detail.BeerDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.brewery.app.presentation.beers.detail.BeerDetailActivity.ARGS_BEER_LIST;
import static com.brewery.app.presentation.beers.detail.BeerDetailActivity.ARGS_BEER_OBJECT_POSITION;

public class BeerFragment extends BaseFragment implements BeerContract.View, SearchView.OnQueryTextListener {

    private Unbinder mUnbinder;

    private BeerAdapter mBeerAdapter;

    private BeerComponent mBeerComponent;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.beers)
    RecyclerView mBeers;

    @BindView(R.id.progress_load_list)
    ProgressBar mProgress;

    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.text_view_no_beers)
    TextView mEmptyStateMessage;

    @Inject
    BeerPresenter mPresenter;

    private List<Beer> mBeersList;

    private MenuItem mItemMenu;

    public BeerFragment() {
        // Required empty public constructor
    }

    public static BeerFragment newInstance() {
        return new BeerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mUnbinder = ButterKnife.bind(this, view);

        getActivity().setTitle(getString(R.string.title_home));
        initilizeInjector();
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.mBeers.setLayoutManager(this.mLayoutManager);
        this.mBeers.setHasFixedSize(true);
        this.mBeerAdapter = new BeerAdapter(null, new BeerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Beer beer, int position) {
                mPresenter.handleBeerListClick(beer, position);
            }
        });
        this.mBeers.setAdapter(this.mBeerAdapter);
        this.mPresenter.attachView(this);
        this.mPresenter.loadBeers();

        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadBeers();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        mItemMenu = menu.findItem(R.id.action_search);
        mItemMenu.setVisible(false);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mItemMenu);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
            this.mUnbinder = null;
        }
        this.mBeerAdapter = null;
        this.mLayoutManager = null;
        if (this.mPresenter != null) {
            this.mPresenter.detachView();
            this.mPresenter = null;
        }
        this.mBeersList = null;
        this.mBeerComponent = null;
        super.onDestroy();
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void displayProgress() {
        this.mEmptyStateMessage.setVisibility(TextView.GONE);
        if (!this.mProgress.isShown() && mBeerAdapter.getItemCount() == 0) {
            mProgress.setVisibility(ProgressBar.VISIBLE);
        }
    }

    @Override
    public void displayBeers(List<Beer> beers) {
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mEmptyStateMessage.setVisibility(TextView.GONE);
        this.mBeerAdapter.addBeers(beers);
        this.mBeersList = beers;
        if (this.mBeersList != null && !this.mBeersList.isEmpty()) {
            mItemMenu.setVisible(true);
        }
    }

    @Override
    public void displayBeerDetail(Beer beer, int position) {
        final Intent intent = new Intent(getActivity(), BeerDetailActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putInt(ARGS_BEER_OBJECT_POSITION, position);
        bundle.putParcelableArrayList(ARGS_BEER_LIST, (ArrayList<? extends Parcelable>) mBeersList);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void handleBeerListLoadError() {
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mProgress.setVisibility(FrameLayout.GONE);
        if (mBeerAdapter.getItemCount() == 0) {
            this.mEmptyStateMessage.setVisibility(TextView.VISIBLE);
        } else {
            Snackbar.make(mBeers, getString(R.string.text_view_no_beers_warning), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void initilizeInjector() {
        this.mBeerComponent = DaggerBeerComponent.builder()
                .applicationModule(new ApplicationModule((BreweryApplication) getActivity().getApplicationContext()))
                .build();
        mBeerComponent.inject(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Beer> filteredGuestList = filter(mBeersList, query);
        mBeerAdapter.setFilter(filteredGuestList);
        return true;
    }

    private List<Beer> filter(List<Beer> beers, String query) {
        final String newQuery = query.toLowerCase();

        final List<Beer> filteredBeerList = new ArrayList<>();
        if (beers != null) {
            for (Beer beer : beers) {
                if (beer.getDisplayName().contains(newQuery)) {
                    filteredBeerList.add(beer);
                }
            }
        }
        return filteredBeerList;
    }
}