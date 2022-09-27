package com.example.showerendorphins.item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.showerendorphins.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowerItem {
    private Integer showerId;
    private Integer usercode;
    private double height;
    private String feeling;
    private double bodyTemperature;
    private double waterTemperature;
    private String aromaKoname;
    private float rating;
    private Drawable img;
    private LocalDateTime date;

    public ShowerItem() {
    }

    public ShowerItem(Integer showerId, double height, String feeling,
                      double bodyTemperature,double waterTemperature,
                      String aromaKoname, float rating,LocalDateTime date,Context context) {
        this.showerId = showerId;
//        this.usercode = usercode;
        this.height = height;
        this.feeling = feeling;
        this.bodyTemperature = bodyTemperature;
        this.waterTemperature = waterTemperature;
        this.aromaKoname = aromaKoname;
        this.rating = rating;
        this.date = date;

        if (feeling.equals("HAPPY")) {
            setImg(context, R.drawable.button_custom);
        }else if (feeling.equals("SAD")) {
            setImg(context, R.drawable.button_custom);
        }else if (feeling.equals("ANGRY")) {
            setImg(context, R.drawable.button_custom);
        }
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

    public double getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public String getAromaKoname() {
        return aromaKoname;
    }

    public void setAromaKoname(String aromaKoname) {
        this.aromaKoname = aromaKoname;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String printDateStr() {
        String formatDate = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시"));
        return formatDate;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Drawable getImg() {

        return img;
    }

    public void setImg(Context context, int imgId) {
        this.img = ContextCompat.getDrawable(context, imgId);
    }
}
