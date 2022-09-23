package com.hanium.showerendorphins.enums;

public enum FeelingStatus {
    ANGRY, SAD, HAPPY;

    public static FeelingStatus fromString(String str) {
        switch(str) {
            case "ANGRY":
                return ANGRY;
            case "SAD":
                return SAD;
            case "HAPPY":
                return HAPPY;
        }
        return null;
    }
}
