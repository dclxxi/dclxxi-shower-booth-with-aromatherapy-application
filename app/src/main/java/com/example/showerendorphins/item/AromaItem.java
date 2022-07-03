package com.example.showerendorphins.item;

public class AromaItem {
    private int aromaId;
    private String aromaKoName;
    private String aromaEnName;
    private String note;
    private String scent;
    private String mood;
    private String summary;
    private String caution;
    private String imgUrl;

    public AromaItem() {}

    public AromaItem(int aromaId, String aromaKoName, String aromaEnName, String note, String scent, String imgUrl) {
        this.aromaId = aromaId;
        this.aromaKoName = aromaKoName;
        this.aromaEnName = aromaEnName;
        this.note = note;
        this.scent = scent;
        this.imgUrl = imgUrl;
    }

    public AromaItem(int aromaId, String aromaKoName, String aromaEnName, String note, String scent, String mood, String summary, String caution, String imgUrl) {
        this.aromaId = aromaId;
        this.aromaKoName = aromaKoName;
        this.aromaEnName = aromaEnName;
        this.note = note;
        this.scent = scent;
        this.mood = mood;
        this.summary = summary;
        this.caution = caution;
        this.imgUrl = imgUrl;
    }

    public int getAromaId() {
        return aromaId;
    }

    public void setAromaId(int aromaId) {
        this.aromaId = aromaId;
    }

    public String getAromaKoName() {
        return aromaKoName;
    }

    public void setAromaKoName(String aromaKoName) {
        this.aromaKoName = aromaKoName;
    }

    public String getAromaEnName() {
        return aromaEnName;
    }

    public void setAromaEnName(String aromaEnName) {
        this.aromaEnName = aromaEnName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getScent() {
        return scent;
    }

    public void setScent(String scent) {
        this.scent = scent;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
