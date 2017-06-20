package com.brewery.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Beer implements Parcelable {

    private String name;

    @SerializedName("nameDisplay")
    private String displayName;

    @SerializedName("abv")
    private String alcoholPercentage;

    private int styleId;

    @SerializedName("isOrganic")
    private String organic;

    @SerializedName("labels")
    private Image image;

    private String description;

    private String createDate;

    private String updateDate;

    protected Beer(Parcel in) {
        name = in.readString();
        displayName = in.readString();
        alcoholPercentage = in.readString();
        styleId = in.readInt();
        organic = in.readString();
        image = in.readParcelable(Image.class.getClassLoader());
        description = in.readString();
        createDate = in.readString();
        updateDate = in.readString();
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(String alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getOrganic() {
        return organic;
    }

    public void setOrganic(String organic) {
        this.organic = organic;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(displayName);
        parcel.writeString(alcoholPercentage);
        parcel.writeInt(styleId);
        parcel.writeString(organic);
        parcel.writeParcelable(image, i);
        parcel.writeString(description);
        parcel.writeString(createDate);
        parcel.writeString(updateDate);
    }
}