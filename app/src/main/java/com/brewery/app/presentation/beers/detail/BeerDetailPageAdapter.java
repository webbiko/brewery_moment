package com.brewery.app.presentation.beers.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.brewery.app.data.model.Beer;

import java.util.ArrayList;
import java.util.List;

public class BeerDetailPageAdapter extends FragmentPagerAdapter {

    private List<Beer> mBeers;

    public BeerDetailPageAdapter(FragmentManager fm, final ArrayList<Beer> beers) {
        super(fm);
        this.mBeers = beers;
    }

    @Override
    public Fragment getItem(int position) {
        return BeerDetailFragment.newInstance(this.mBeers.get(position));
    }

    @Override
    public int getCount() {
        return this.mBeers == null ? 0 : this.mBeers.size();
    }
}