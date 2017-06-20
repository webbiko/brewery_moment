package com.brewery.app.presentation.beers;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

    private OnItemClickListener mListener;

    private List<Beer> mBeers;

    public BeerAdapter(final List<Beer> beers, final OnItemClickListener listener) {
        this.mBeers = beers;
        this.mListener = listener;
    }

    @Override
    public BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_beer, parent, false);

        return new BeerViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        if (holder != null) {
            holder.bind(this.mBeers.get(position), this.mListener, position);
        }
    }

    @Override
    public int getItemCount() {
        return this.mBeers == null ? 0 : this.mBeers.size();
    }

    class BeerViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout container;
        ImageView imageView;
        TextView beerDisplayName;
        TextView beerName;
        ProgressBar progress;

        public BeerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_beverage);
            beerDisplayName = (TextView) itemView.findViewById(R.id.text_view_beer_display_name);
            beerName = (TextView) itemView.findViewById(R.id.text_view_beer_name);
            container = (ConstraintLayout) itemView.findViewById(R.id.item_beer_container);
            progress = (ProgressBar) itemView.findViewById(R.id.progress_load_image);
        }

        public void bind(final Beer beer, final OnItemClickListener listener, final int position) {
            if (beer != null) {
                if (beer.getImage() != null && beer.getImage().getIconUrl() != null) {
                    displayBeerInformation(beer);
                } else {
                    Glide.clear(imageView);
                    imageView.setImageResource(R.drawable.ic_beer);
                    progress.setVisibility(ProgressBar.GONE);
                }

                beerName.setText(beer.getName());
                beerDisplayName.setText(beer.getDisplayName());
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(beer, position);
                    }
                });
            }
        }

        private void displayBeerInformation(Beer beer) {
            Glide
                    .with(imageView.getContext())
                    .load(beer.getImage().getIconUrl())
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

    public void addBeers(final List<Beer> beers) {
        this.mBeers = beers;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(final Beer beer, final int position);
    }

    public void setFilter(List<Beer> beers) {
        mBeers = new ArrayList<>();
        mBeers.addAll(beers);
        notifyDataSetChanged();
    }
}