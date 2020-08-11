package com.example.vsmtiinfo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Godina implements Parcelable {
    @SerializedName("Godina")
    private String godina;
    @SerializedName("dataG")
    private ArrayList<Semestar> semestar;

    public Godina(String godina, ArrayList<Semestar> semestar) {
        this.godina = godina;
        this.semestar = semestar;
    }

    protected Godina(Parcel in) {
        godina = in.readString();
    }

    public static final Creator<Godina> CREATOR = new Creator<Godina>() {
        @Override
        public Godina createFromParcel(Parcel in) {
            return new Godina(in);
        }

        @Override
        public Godina[] newArray(int size) {
            return new Godina[size];
        }
    };

    public String getGodina() {
        return godina;
    }

    public ArrayList<Semestar> getSemestar() {
        return semestar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(godina);
    }
}
