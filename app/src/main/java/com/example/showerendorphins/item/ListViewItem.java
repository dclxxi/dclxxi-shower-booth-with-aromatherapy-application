package com.example.showerendorphins.item;

public class ListViewItem {
    private Integer aromaId;
    private String koName;
    private String enName;
    private String imgUrl;
    private Boolean isChecked;

    public Integer getAromaId() {
        return aromaId;
    }

    public void setAromaId(Integer aromaId) {
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
