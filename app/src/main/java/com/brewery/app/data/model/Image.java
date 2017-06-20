package com.brewery.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    @SerializedName("icon")
    private String iconUrl;

    @SerializedName("medium")
    private String iconMediumUrl;

    @SerializedName("large")
    private String iconLargeUrl;

    @SerializedName("squareMedium")
    private String squareMediumUrl;

    @SerializedName("squareLarge")
    private String squareLargeUrl;

    public Image() {
        // default constructor
    }

    public Image(Parcel in) {
        iconUrl = in.readString();
        iconMediumUrl = in.readString();
        iconLargeUrl = in.readString();
        squareMediumUrl = in.readString();
        squareLargeUrl = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconMediumUrl() {
        return iconMediumUrl;
    }

    public void setIconMediumUrl(String iconMediumUrl) {
        this.iconMediumUrl = iconMediumUrl;
    }

    public String getIconLargeUrl() {
        return iconLargeUrl;
    }

    public void setIconLargeUrl(String iconLargeUrl) {
        this.iconLargeUrl = iconLargeUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(iconUrl);
        parcel.writeString(iconMediumUrl);
        parcel.writeString(iconLargeUrl);
        parcel.writeString(squareMediumUrl);
        parcel.writeString(squareLargeUrl);
    }

    public String getSquareMediumUrl() {
        return squareMediumUrl;
    }

    public void setSquareMediumUrl(String squareMediumUrl) {
        this.squareMediumUrl = squareMediumUrl;
    }

    public String getSquareLargeUrl() {
        return squareLargeUrl;
    }

    public void setSquareLargeUrl(String squareLargeUrl) {
        this.squareLargeUrl = squareLargeUrl;
    }
}