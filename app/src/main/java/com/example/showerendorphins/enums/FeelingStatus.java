package com.example.showerendorphins.enums;

public enum FeelingStatus {
    ANGRY {
        @Override
        public String toString() {
            return "ANGRY";
        }
    },
    SAD {
        @Override
        public String toString() {
            return "SAD";
        }
    },
    HAPPY {
        @Override
        public String toString() {
            return "HAPPY";
        }
    };


}
