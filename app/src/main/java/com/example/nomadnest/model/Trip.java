package com.example.nomadnest.model;

public class Trip {
    public Trip(String destination, String description, int destinationImage) {
        this.destination = destination;
        this.description = description;
        this.destinationImage = destinationImage;
    }

    String destination;
    String description;
    int destinationImage;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDestinationImage() {
        return destinationImage;
    }

    public void setDestinationImage(int destinationImage) {
        this.destinationImage = destinationImage;
    }
}
