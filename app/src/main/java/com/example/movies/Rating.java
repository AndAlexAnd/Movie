package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("kp")
    private String kp;

    public Rating(String kp) {
        this.kp = kp;
    }

    public String getKp() {
        return kp.substring(0,3);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "kp='" + kp + '\'' +
                '}';
    }
}
