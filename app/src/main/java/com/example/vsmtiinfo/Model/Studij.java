package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Studij {
    @SerializedName("NazivStudija")
    private String nazivStudija;
    @SerializedName("data")
    private StudijData studijData;

    public Studij(String nazivStudija, StudijData studijData) {
        this.nazivStudija = nazivStudija;
        this.studijData = studijData;
    }

    public String getNazivStudija() {
        return nazivStudija;
    }

    public StudijData getStudijData() {
        return studijData;
    }
}
