package com.brewery.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.brewery.app.R;
import com.brewery.app.data.model.Brewery;
import com.brewery.app.presentation.about.AboutFragment;
import com.brewery.app.presentation.base.BaseActivity;
import com.brewery.app.presentation.beers.BeerFragment;
import com.brewery.app.presentation.breweries.BreweryListFragment;
import com.brewery.app.presentation.breweries.details.BreweryContainerActivity;

import static com.brewery.app.presentation.breweries.details.BreweryContainerActivity.ARGS_BREWERY_OBJECT;

public class HomeActivity extends BaseActivity implements BreweryListFragment.OnBreweryCardClick {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (!hasCurrentFragment(R.id.beer_container)) {
                        setScreenTitle(getString(R.string.title_home));
                        setToolbarState(true);
                        setCurrentFragment(R.id.content, BeerFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_brewery:
                    if (!hasCurrentFragment(R.id.beer_container)) {
                        setToolbarState(false);
                        setCurrentFragment(R.id.content, BreweryListFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_about:
                    if (!hasCurrentFragment(R.id.about_container)) {
                        setScreenTitle(getString(R.string.title_about));
                        setToolbarState(true);
                        setCurrentFragment(R.id.content, AboutFragment.newInstance());
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!hasCurrentFragment(R.id.beer_container)) {
            setCurrentFragment(R.id.content, BeerFragment.newInstance());
        }

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void displayBreweryDetails(Brewery brewery) {
        final Intent intent = new Intent(this, BreweryContainerActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_BREWERY_OBJECT, brewery);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}