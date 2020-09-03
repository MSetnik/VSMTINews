package com.example.vsmtiinfo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Dokument implements Parcelable {
    @SerializedName("id")
    private int id;
    @SerializedName("naslov")
    private String naslov;
    @SerializedName("opis")
    private String opis;
    @SerializedName("url")
    private String url;


    public Dokument(int id, String naslov, String opis, String url) {
        this.id = id;
        this.naslov = naslov;
        this.opis = opis;
        this.url = url;
    }

    protected Dokument(Parcel in) {
        id = in.readInt();
        naslov = in.readString();
        opis = in.readString();
        url = in.readString();
    }

    public static final Creator<Dokument> CREATOR = new Creator<Dokument>() {
        @Override
        public Dokument createFromParcel(Parcel in) {
            return new Dokument(in);
        }

        @Override
        public Dokument[] newArray(int size) {
            return new Dokument[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getOpis() {
        return opis;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(naslov);
        dest.writeString(opis);
        dest.writeString(url);
    }
}
