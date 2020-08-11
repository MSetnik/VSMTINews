package com.example.vsmtiinfo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudijskiProgram implements Parcelable {
    @SerializedName("NazivStudijskogPrograma")
    private String nazivStudijskogPrograma;
    @SerializedName("StudijskiSmjer")
    private ArrayList<StudijskiSmjer> studijskiSmjer;
    private boolean expanded;

    protected StudijskiProgram(Parcel in) {
        nazivStudijskogPrograma = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<StudijskiProgram> CREATOR = new Creator<StudijskiProgram>() {
        @Override
        public StudijskiProgram createFromParcel(Parcel in) {
            return new StudijskiProgram(in);
        }

        @Override
        public StudijskiProgram[] newArray(int size) {
            return new StudijskiProgram[size];
        }
    };

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public StudijskiProgram(String nazivStudijskogPrograma, ArrayList<StudijskiSmjer> studijskiSmjer) {
        this.nazivStudijskogPrograma = nazivStudijskogPrograma;
        this.studijskiSmjer = studijskiSmjer;
        expanded = false;
    }

    public String getNazivStudijskogPrograma() {
        return nazivStudijskogPrograma;
    }

    public ArrayList<StudijskiSmjer> getStudijskiSmjer() {
        return studijskiSmjer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nazivStudijskogPrograma);
        parcel.writeByte((byte) (expanded ? 1 : 0));
    }
}
