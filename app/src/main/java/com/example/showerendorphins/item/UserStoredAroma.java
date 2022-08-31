package com.example.showerendorphins.item;

import java.util.List;

public class UserStoredAroma {
    private String userId;
    private List<Integer> aromaId;

    public UserStoredAroma() {
    }

    public UserStoredAroma(String userId, List<Integer> aromaId) {
        this.userId = userId;
        this.aromaId = aromaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Integer> getAromaId() {
        return aromaId;
    }

    public void setAromaId(List<Integer> aromaId) {
        this.aromaId = aromaId;
    }
}
