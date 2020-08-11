package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudijskiSmjer {
    @SerializedName("NazivStudijskogSmjera")
    private String nazivStudijskogSmjera;
    @SerializedName("Studij")
    private ArrayList<Studij> studij;
    private boolean isExpanded;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public StudijskiSmjer(String nazivStudijskogSmjera, ArrayList<Studij> studij) {
        this.nazivStudijskogSmjera = nazivStudijskogSmjera;
        this.studij = studij;
        isExpanded = false;
    }

    public String getNazivPrograma() {
        return nazivStudijskogSmjera;
    }

    public  ArrayList<Studij> getStudijskiSmjer() {
        return studij;
    }
}
