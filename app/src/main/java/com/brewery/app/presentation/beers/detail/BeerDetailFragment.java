package com.brewery.app.presentation.beers.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brewery.app.R;
import com.brewery.app.data.model.Beer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.brewery.app.presentation.beers.detail.BeerDetailActivity.ARGS_BEER_OBJECT;

public class BeerDetailFragment extends Fragment {

    private Beer mBeer;

    private Unbinder mUnbinder;

    @BindView(R.id.image_view_beer)
    ImageView mImageViewBeer;

    @BindView(R.id.text_view_beer_description)
    TextView mTextViewBeerDescription;

    @BindView(R.id.progress_load_image)
    ProgressBar mProgress;

    @BindView(R.id.main_content_container)
    ConstraintLayout mMainContentContainer;

    @BindView(R.id.text_view_no_beer_detail)
    TextView mEmptyStateMessage;

    public BeerDetailFragment() {
        // Required empty public constructor
    }

    public static BeerDetailFragment newInstance(Beer beer) {
        BeerDetailFragment fragment = new BeerDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_BEER_OBJECT, beer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBeer = getArguments().getParcelable(ARGS_BEER_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beer_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mUnbinder = ButterKnife.bind(this, view);

        displayBeerDetails(mBeer);
    }

    private void displayBeerDetails(Beer beer) {
        if (beer.getDescription() != null) {
            this.mTextViewBeerDescription.setText(beer.getDescription());

            if (beer.getImage() != null && beer.getImage().getIconMediumUrl() != null) {
                mProgress.setVisibility(ProgressBar.VISIBLE);
                Glide.with(this)
                        .load(beer.getImage().getIconMediumUrl())
                        .crossFade()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                mProgress.setVisibility(ProgressBar.GONE);
                                mImageViewBeer.setImageResource(R.drawable.ic_beer);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                mProgress.setVisibility(ProgressBar.GONE);
                                return false;
                            }
                        })
                        .into(this.mImageViewBeer);
            } else {
                mProgress.setVisibility(ProgressBar.GONE);
                Glide.clear(this.mImageViewBeer);
                this.mImageViewBeer.setImageResource(R.drawable.ic_beer);
            }
        } else {
            mProgress.setVisibility(ProgressBar.GONE);
            mMainContentContainer.setVisibility(ConstraintLayout.GONE);
            mEmptyStateMessage.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
            this.mUnbinder = null;
        }
    }
}
