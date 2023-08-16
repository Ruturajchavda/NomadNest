package com.example.nomadnest.model;

import java.io.Serializable;

public class Places implements Serializable {

    private int placeId;
    private String placeName;
    private String placeLocation;
    private String ratings;
    private int placeImage;

    public Places() {

    }

    public Places(int placeId, String placeName, String placeLocation, String ratings, int placeImage) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeLocation = placeLocation;
        this.ratings = ratings;
        this.placeImage = placeImage;
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

    @Override
    public String toString() {
        return "Places{" +
                "placeId=" + placeId +
                ", placeName='" + placeName + '\'' +
                ", placeLocation='" + placeLocation + '\'' +
                ", ratings=" + ratings +
                ", placeImage=" + placeImage +
                '}';
    }
}
