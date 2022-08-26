package com.example.showerendorphins.item;

public class ShowerItem {
    private Integer showerId;
    private Integer usercode;
    private double height;
    private String feeling;
    private double bodyTemperature;
    private String aromaKoname;
    private double rating;

    public ShowerItem() {
    }

    public ShowerItem(Integer showerId, Integer usercode, double height, String feeling, double bodyTemperature, String aromaKoname, double rating) {
        this.showerId = showerId;
        this.usercode = usercode;
        this.height = height;
        this.feeling = feeling;
        this.bodyTemperature = bodyTemperature;
        this.aromaKoname = aromaKoname;
        this.rating = rating;
    }

    public Integer getShowerId() {
        return showerId;
    }

    public void setShowerId(Integer showerId) {
        this.showerId = showerId;
    }

    public Integer getUsercode() {
        return usercode;
    }

    public void setUsercode(Integer usercode) {
        this.usercode = usercode;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public double getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public String getAromaKoname() {
        return aromaKoname;
    }

    public void setAromaKoname(String aromaKoname) {
        this.aromaKoname = aromaKoname;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
