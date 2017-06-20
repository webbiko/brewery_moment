package com.brewery.app.presentation.beers.detail;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.brewery.app.R;
import com.brewery.app.data.model.Beer;
import com.brewery.app.presentation.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BeerDetailActivity extends BaseActivity {

    public static final String ARGS_BEER_OBJECT_POSITION = "ARGS_BEER_OBJECT_POSITION";
    public static final String ARGS_BEER_OBJECT = "ARGS_BEER_OBJECT";
    public static final String ARGS_BEER_LIST = "ARGS_BEER_LIST";

    private Unbinder mUnbinder;

    private PagerAdapter mPagerAdapter;

    @BindView(R.id.beer_detail_pager)
    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);
        this.mUnbinder = ButterKnife.bind(this);

        setTitle(getString(R.string.title_beer_detail));
        setUpButton(true);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(ARGS_BEER_LIST)) {
            int position = 0;
            if (bundle.containsKey(ARGS_BEER_OBJECT_POSITION)) {
                position = bundle.getInt(ARGS_BEER_OBJECT_POSITION);
            }
            final ArrayList<Beer> beers = bundle.getParcelableArrayList(ARGS_BEER_LIST);
            mPagerAdapter = new BeerDetailPageAdapter(getSupportFragmentManager(), beers);
            mPager.setClipToPadding(false);
            mPager.setPadding(40, 0, 40, 0);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10 * 2, getResources().getDisplayMetrics());
            mPager.setPageMargin(-margin);

            mPager.setPageTransformer(true, new ZoomOutPageTransformer());
            mPager.setAdapter(mPagerAdapter);
            mPager.setCurrentItem(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    //
    @Override
    protected void onDestroy() {

        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
            this.mUnbinder = null;
        }
        this.mPagerAdapter = null;
        super.onDestroy();
    }
}
