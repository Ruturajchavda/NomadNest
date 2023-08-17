package com.example.nomadnest.model;

import java.io.Serializable;
import java.util.Date;

public class Bookings implements Serializable {

    private int bookingID;
    private int totalPeople;
    private Date travellingDate;
    private int tripDuration;
    private String tripType;
    private float tripBudget;
    private int placeId;

    public Bookings() {

    }

    public Bookings(int bookingID, int totalPeople, Date travellingDate, int tripDuration, String tripType, float tripBudget, int placeId) {
        this.bookingID = bookingID;
        this.totalPeople = totalPeople;
        this.travellingDate = travellingDate;
        this.tripDuration = tripDuration;
        this.tripType = tripType;
        this.tripBudget = tripBudget;
        this.placeId = placeId;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public Date getTravellingDate() {
        return travellingDate;
    }

    public void setTravellingDate(Date travellingDate) {
        this.travellingDate = travellingDate;
    }

    public int getTripDuration() {
        return tripDuration;
    }

    public void setTripDuration(int tripDuration) {
        this.tripDuration = tripDuration;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public float getTripBudget() {
        return tripBudget;
    }

    public void setTripBudget(float tripBudget) {
        this.tripBudget = tripBudget;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "bookingID=" + bookingID +
                ", totalPeople=" + totalPeople +
                ", travellingDate=" + travellingDate +
                ", tripDuration=" + tripDuration +
                ", tripType='" + tripType + '\'' +
                ", tripBudget=" + tripBudget +
                ", placeId=" + placeId +
                '}';
    }
}
