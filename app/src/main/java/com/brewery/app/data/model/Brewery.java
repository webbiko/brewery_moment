package com.brewery.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Brewery implements Parcelable {
    private String name;

    private String nameShortDisplay;

    private String description;

    private String website;

    private String established;

    private Image images;

    protected Brewery(Parcel in) {
        name = in.readString();
        nameShortDisplay = in.readString();
        description = in.readString();
        website = in.readString();
        established = in.readString();
        images = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Creator<Brewery> CREATOR = new Creator<Brewery>() {
        @Override
        public Brewery createFromParcel(Parcel in) {
            return new Brewery(in);
        }

        @Override
        public Brewery[] newArray(int size) {
            return new Brewery[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameShortDisplay() {
        return nameShortDisplay;
    }

    public void setNameShortDisplay(String nameShortDisplay) {
        this.nameShortDisplay = nameShortDisplay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEstablished() {
        return established;
    }

    public void setEstablished(String established) {
        this.established = established;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(nameShortDisplay);
        parcel.writeString(description);
        parcel.writeString(website);
        parcel.writeString(established);
        parcel.writeParcelable(images, i);
    }
}