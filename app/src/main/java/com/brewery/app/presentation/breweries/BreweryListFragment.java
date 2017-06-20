package com.brewery.app.presentation.breweries;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.brewery.app.data.model.Brewery;
import com.brewery.app.di.component.BreweryComponent;
import com.brewery.app.di.component.DaggerBreweryComponent;
import com.brewery.app.di.module.ApplicationModule;
import com.brewery.app.infraestructure.BreweryApplication;
import com.brewery.app.infraestructure.ExpansiveLayoutManager;
import com.brewery.app.presentation.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BreweryListFragment extends BaseFragment implements BreweryContract.View, SearchView.OnQueryTextListener {

    @Inject
    BreweryPresenter mPresenter;

    @BindView(R.id.recycler_view_breweries)
    RecyclerView mRecyclerViewBreweries;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.progress)
    ProgressBar mProgress;

    @BindView(R.id.text_view_no_brewery)
    TextView mEmptyStateMessage;

    private BreweryComponent mBreweryComponent;

    private RecyclerView.LayoutManager mLayoutManager;

    private BreweryListAdapter mAdapter;

    private Unbinder mUnbinder;

    private List<Brewery> mBreweries;

    private MenuItem mItemMenu;

    private OnBreweryCardClick mOnBreweryCardClick;

    public static BreweryListFragment newInstance() {
        return new BreweryListFragment();
    }

    public BreweryListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brewery_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mUnbinder = ButterKnife.bind(this, view);

        initilizeInjector();
        this.mPresenter.attachView(this);

        this.mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.AppThemeCollapsedAppBar);
        this.mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.AppThemeExpandedAppBar);
        this.mCollapsingToolbarLayout.setTitle(getString(R.string.title_brewery));

        this.mLayoutManager = new ExpansiveLayoutManager(getActivity());
        this.mRecyclerViewBreweries.setLayoutManager(this.mLayoutManager);
        this.mRecyclerViewBreweries.setHasFixedSize(true);
        this.mAdapter = new BreweryListAdapter(new BreweryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Brewery brewery, int position) {
                mPresenter.handleBreweryListClick(brewery);
            }
        });
        this.mRecyclerViewBreweries.setAdapter(this.mAdapter);
        this.mPresenter.loadBreweries();

        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadBreweries();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mOnBreweryCardClick = (OnBreweryCardClick) context;
    }

    @Override
    public void displayBreweries(List<Brewery> breweries) {
        mItemMenu.setVisible(true);
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mBreweries = breweries;
        this.mAdapter.addBeers(breweries);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void displayProgress() {
        this.mEmptyStateMessage.setVisibility(TextView.GONE);
        if (!this.mProgress.isShown()) {
            this.mProgress.setVisibility(ProgressBar.VISIBLE);
        }
    }

    @Override
    public void handleBreweryListLoadError() {
        this.mSwipeRefreshLayout.setRefreshing(false);
        this.mProgress.setVisibility(FrameLayout.GONE);
        if (mAdapter.getItemCount() == 0) {
            this.mEmptyStateMessage.setVisibility(TextView.VISIBLE);
        } else {
            Snackbar.make(mRecyclerViewBreweries,
                    getString(R.string.text_view_no_breweries_warning), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displayBreweryDetails(Brewery brewery) {
        this.mOnBreweryCardClick.displayBreweryDetails(brewery);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Brewery> filteredGuestList = filter(mBreweries, query);
        mAdapter.setFilter(filteredGuestList);
        return true;
    }

    private List<Brewery> filter(List<Brewery> breweries, String query) {
        final String newQuery = query.toLowerCase();

        final List<Brewery> filteredBreweyList = new ArrayList<>();
        if (breweries != null) {
            for (Brewery brewery : breweries) {
                if (brewery.getName().contains(newQuery)) {
                    filteredBreweyList.add(brewery);
                }
            }
        }
        return filteredBreweyList;
    }

    private void initilizeInjector() {
        this.mBreweryComponent = DaggerBreweryComponent.builder()
                .applicationModule(new ApplicationModule((BreweryApplication) getActivity().getApplicationContext()))
                .build();
        this.mBreweryComponent.inject(this);
    }

    @Override
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
            this.mUnbinder = null;
        }
        this.mAdapter = null;
        this.mLayoutManager = null;
        if (this.mPresenter != null) {
            this.mPresenter.detachView();
            this.mPresenter = null;
        }
        this.mBreweries = null;
        this.mBreweryComponent = null;
        super.onDestroy();
    }

    public interface OnBreweryCardClick {
        void displayBreweryDetails(final Brewery brewery);
    }
}