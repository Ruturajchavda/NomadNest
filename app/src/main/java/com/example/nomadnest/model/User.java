package com.example.nomadnest.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String email;
    String phone;
    Bitmap profilePicture;
    private String password;

    public User(){

    }

    public User(String name, String email, String phone, Bitmap profilePicture, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.password = password;
    }

    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", profilePicture=" + profilePicture +
                ", password='" + password + '\'' +
                '}';
    }
}
