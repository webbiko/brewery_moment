package com.brewery.app.presentation.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.brewery.app.R;

/**
 * This class is used as base for all activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        @SuppressLint("InflateParams")
        final CoordinatorLayout fullView =
                (CoordinatorLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        final FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.containerView);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        configureToolBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        configureToolBar();
    }

    /**
     * Override this method if you need to disable toolbar
     *
     * @return True if toolbar should display otherwise false
     */
    protected boolean useToolbar() {
        return true;
    }

    private void configureToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (useToolbar()) {
                setSupportActionBar(toolbar);
            } else {
                toolbar.setVisibility(View.GONE);
            }
        }
    }

    protected void setToolbarState(final boolean enable) {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final AppBarLayout appBar = (AppBarLayout) findViewById(R.id.appbar);
        if (toolbar != null && appBar != null) {
            if (enable) {
                setSupportActionBar(toolbar);

                toolbar.setVisibility(View.VISIBLE);
                appBar.setVisibility(AppBarLayout.VISIBLE);
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.containerView);
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();

                // Calculate ActionBar height
                int actionBarHeight = 0;
                TypedValue tv = new TypedValue();
                if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
                }

                lp.setMargins(0, actionBarHeight, 0, 0);
                frameLayout.setLayoutParams(lp);
            } else {
                toolbar.setVisibility(View.GONE);
                appBar.setVisibility(AppBarLayout.GONE);

                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.containerView);
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();
                lp.setMargins(0, 0, 0, 0);
                frameLayout.setLayoutParams(lp);
            }
        }
    }

    protected <F extends BaseFragment> boolean hasCurrentFragment(int containerId) {
        return getCurrentFragment(containerId, BaseFragment.class) != null;
    }

    protected <F extends BaseFragment> F getCurrentFragment(int containerId, Class<F> fragmentClass) {
        return (F) getFragmentManager().findFragmentById(containerId);
    }

    protected <F extends BaseFragment> void setCurrentFragment(int containerId, F fragment) {
        getFragmentManager().beginTransaction()
                .replace(containerId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    protected final void setScreenTitle(String title) {
        setTitle(null);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    protected final void setUpButton(final boolean enable) {
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(enable);
        }
    }
}