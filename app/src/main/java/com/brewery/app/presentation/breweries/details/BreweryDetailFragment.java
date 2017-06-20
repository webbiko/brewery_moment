package com.brewery.app.presentation.breweries.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.brewery.app.R;
import com.brewery.app.data.model.Brewery;
import com.brewery.app.presentation.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.brewery.app.presentation.breweries.details.BreweryContainerActivity.ARGS_BREWERY_OBJECT;

public class BreweryDetailFragment extends BaseFragment {

    private Unbinder mUnbinder;

    @BindView(R.id.image_view_brewery)
    ImageView mBreweryImage;

    @BindView(R.id.progress_load_image)
    ProgressBar mProgress;

    @BindView(R.id.button_back)
    Button mButtonBack;

    private OnBreweryDetailClick mOnBreweryDetailClick;

    private Brewery mBrewery;

    public static BreweryDetailFragment newInstance(final Brewery brewery) {
        final BreweryDetailFragment fragment = new BreweryDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_BREWERY_OBJECT, brewery);
        fragment.setArguments(bundle);
        return fragment;
    }

    public BreweryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brewery_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mUnbinder = ButterKnife.bind(this, view);

        final Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(ARGS_BREWERY_OBJECT)) {
            this.mBrewery = bundle.getParcelable(ARGS_BREWERY_OBJECT);
            this.mProgress.setVisibility(ProgressBar.VISIBLE);
            if (this.mBrewery.getImages() != null && this.mBrewery.getImages().getSquareMediumUrl() != null) {
                Glide.with(this)
                        .load(this.mBrewery.getImages().getSquareMediumUrl())
                        .crossFade()
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                mProgress.setVisibility(ProgressBar.GONE);
                                mBreweryImage.setImageResource(R.drawable.placeholder_no_image);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                mProgress.setVisibility(ProgressBar.GONE);
                                return false;
                            }
                        })
                        .into(this.mBreweryImage);
            } else {
                mProgress.setVisibility(ProgressBar.GONE);
                Glide.clear(this.mBreweryImage);
                this.mBreweryImage.setImageResource(R.drawable.placeholder_no_image);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnBreweryDetailClick = (OnBreweryDetailClick) context;
    }

    @OnClick(R.id.button_back)
    public void clickBackDetails() {
        this.mOnBreweryDetailClick.flipCard(this.mBrewery);
    }

    @OnClick(R.id.main_content_container)
    public void clickOnCard() {
        this.mOnBreweryDetailClick.flipCard(this.mBrewery);
    }

    @Override
    public void onDestroy() {
        if (this.mUnbinder != null) {
            this.mUnbinder.unbind();
            this.mUnbinder = null;
        }
        this.mBrewery = null;
        this.mOnBreweryDetailClick = null;
        super.onDestroy();
    }
}
