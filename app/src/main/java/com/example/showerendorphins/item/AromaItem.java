package com.example.showerendorphins.item;

import android.os.Parcel;
import android.os.Parcelable;

public class AromaItem implements Parcelable {
    private int aromaId;
    private String koName;
    private String enName;
    private String note;
    private String feeling;
    private String scent;
    private String summary;
    private String caution;
    private String imgUrl;

    public AromaItem() {}

    public AromaItem(int aromaId, String koName, String enName, String note, String scent, String imgUrl) {
        this.aromaId = aromaId;
        this.koName = koName;
        this.enName = enName;
        this.note = note;
        this.scent = scent;

        if (imgUrl.equals("null")) {
            this.imgUrl = "https://cdn-icons-png.flaticon.com/512/4200/4200467.png";
        } else {
            this.imgUrl = imgUrl;
        }
    }

    public AromaItem(int aromaId, String koName, String enName, String note, String scent, String mood, String summary, String caution, String imgUrl) {
        this.aromaId = aromaId;
        this.koName = koName;
        this.enName = enName;
        this.note = note;
        this.scent = scent;
        this.feeling = mood;
        this.summary = summary;
        this.caution = caution;
        this.imgUrl = imgUrl;
    }

    protected AromaItem(Parcel in) {
        this.aromaId = in.readInt();
        this.koName = in.readString();
        this.enName = in.readString();
        this.note = in.readString();
        this.scent = in.readString();
        this.feeling = in.readString();
        this.summary = in.readString();
        this.caution = in.readString();
        this.imgUrl = in.readString();
    }

    public static final Creator<AromaItem> CREATOR = new Creator<AromaItem>() {
        @Override
        public AromaItem createFromParcel(Parcel in) {
            return new AromaItem(in);
        }

        @Override
        public AromaItem[] newArray(int size) {
            return new AromaItem[size];
        }
    };

    public int getAromaId() {
        return aromaId;
    }

    public void setAromaId(int aromaId) {
        this.aromaId = aromaId;
    }

    public String getKoName() {
        return koName;
    }

    public void setKoName(String koName) {
        this.koName = koName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
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

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(aromaId);
        parcel.writeString(koName);
        parcel.writeString(enName);
        parcel.writeString(note);
        parcel.writeString(feeling);
        parcel.writeString(scent);
        parcel.writeString(summary);
        parcel.writeString(caution);
        parcel.writeString(imgUrl);
    }
}
