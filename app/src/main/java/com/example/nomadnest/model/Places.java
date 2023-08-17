package com.example.nomadnest.model;

import java.io.Serializable;

public class Places implements Serializable {

    private int placeId;
    private String placeName;
    private String placeDesc;
    private String placeLocation;
    private String ratings;
    private int placeImage;
    private float estimatedPrice;
    private String category;
    private boolean isPopular;

    public Places() {
    }

    public Places(int placeId, String placeName, String placeDesc, String placeLocation, String ratings, int placeImage, float estimatedPrice, String category, boolean isPopular) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeDesc = placeDesc;
        this.placeLocation = placeLocation;
        this.ratings = ratings;
        this.placeImage = placeImage;
        this.estimatedPrice = estimatedPrice;
        this.category = category;
        this.isPopular = isPopular;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public int getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(int placeImage) {
        this.placeImage = placeImage;
    }

    public String getPlaceDesc() {
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    public float getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(float estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    @Override
    public String toString() {
        return "Places{" +
                "placeId=" + placeId +
                ", placeName='" + placeName + '\'' +
                ", placeDesc='" + placeDesc + '\'' +
                ", placeLocation='" + placeLocation + '\'' +
                ", ratings='" + ratings + '\'' +
                ", placeImage=" + placeImage +
                ", estimatedPrice=" + estimatedPrice +
                ", category='" + category + '\'' +
                ", isPopular=" + isPopular +
                '}';
    }
}
