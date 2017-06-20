package com.brewery.app.presentation.breweries.details;

import android.app.FragmentManager;
import android.os.Bundle;

import com.brewery.app.R;
import com.brewery.app.data.model.Brewery;
import com.brewery.app.presentation.base.BaseActivity;

public class BreweryContainerActivity extends BaseActivity implements OnBreweryDetailClick, FragmentManager.OnBackStackChangedListener {

    public static final String ARGS_BREWERY_OBJECT = "ARGS_BREWERY_OBJECT";

    private boolean mShowingBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_container);
        setUpButton(true);

        final Bundle bundle = getIntent().getExtras();
        if (savedInstanceState == null && bundle != null && bundle.containsKey(ARGS_BREWERY_OBJECT) &&
                !hasCurrentFragment(R.id.frame_layout_brewery_container)) {
            final Brewery brewery = bundle.getParcelable(ARGS_BREWERY_OBJECT);
            setCurrentFragment(R.id.container, BreweryFragment.newInstance(brewery));
            setScreenTitle(getString(R.string.title_brewery_detail));
        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        getFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void flipCard(Brewery brewery) {
        manageCardFlip(brewery);
    }

    private void manageCardFlip(final Brewery brewery) {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        mShowingBack = true;
        final BreweryDetailFragment fragment = BreweryDetailFragment.newInstance(brewery);
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }
}