package com.mlclassifier.ecgclaissfier.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DateObject implements Serializable {

    @SerializedName("$date")
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
