package com.brewery.app.presentation.breweries;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brewery.app.R;
import com.brewery.app.data.model.Brewery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class BreweryListAdapter extends RecyclerView.Adapter<BreweryListAdapter.BreweryViewHolder> {

    private BreweryListAdapter.OnItemClickListener mListener;

    private List<Brewery> mBreweries;

    public BreweryListAdapter(final BreweryListAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_brewery, parent, false);

        return new BreweryListAdapter.BreweryViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(BreweryViewHolder holder, int position) {
        if (holder != null) {
            holder.bind(this.mBreweries.get(position), this.mListener, position);
        }
    }

    @Override
    public int getItemCount() {
        return this.mBreweries == null ? 0 : this.mBreweries.size();
    }

    class BreweryViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        ImageView imageView;
        TextView breweryName;
        TextView breweryDescription;
        ProgressBar progress;

        public BreweryViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_beverage);
            breweryName = (TextView) itemView.findViewById(R.id.text_view_brewery_name);
            breweryDescription = (TextView) itemView.findViewById(R.id.text_view_brewery_description);
            container = (RelativeLayout) itemView.findViewById(R.id.item_brewery_container);
            progress = (ProgressBar) itemView.findViewById(R.id.progress_load_image);
        }

        public void bind(final Brewery brewery, final BreweryListAdapter.OnItemClickListener listener, final int position) {
            if (brewery != null) {
                if (brewery.getImages() != null && brewery.getImages().getSquareMediumUrl() != null) {
                    displayBeerInformation(brewery);
                } else {
                    Glide.clear(imageView);
                    imageView.setImageResource(R.drawable.placeholder_no_image);
                    progress.setVisibility(ProgressBar.GONE);
                }

                breweryName.setText(brewery.getName());
                if (brewery.getDescription() != null && !brewery.getDescription().isEmpty()) {
                    breweryDescription.setText(brewery.getDescription());
                } else {
                    breweryDescription.setText(R.string.brewery_no_description_available);
                }
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(brewery, position);
                    }
                });
            }
        }

        private void displayBeerInformation(Brewery brewery) {
            Glide
                    .with(imageView.getContext())
                    .load(brewery.getImages().getSquareMediumUrl())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progress.setVisibility(ProgressBar.GONE);
                            imageView.setImageResource(R.drawable.ic_beer);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progress.setVisibility(ProgressBar.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    public void addBeers(final List<Brewery> breweries) {
        this.mBreweries = breweries;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(final Brewery brewery, final int position);
    }

    public void setFilter(List<Brewery> breweries) {
        mBreweries = new ArrayList<>();
        mBreweries.addAll(breweries);
        notifyDataSetChanged();
    }
}