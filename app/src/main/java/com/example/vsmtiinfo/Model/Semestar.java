package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Semestar {
    @SerializedName("Semestar")
    private String semestar;
    @SerializedName("dataS")
    private ArrayList<Predmet> predmet;

    public Semestar(String semestar, ArrayList<Predmet> predmet) {
        this.semestar = semestar;
        this.predmet = predmet;
    }

    public String getSemestar() {
        return semestar;
    }

    public ArrayList<Predmet> getPredmet() {
        return predmet;
    }

}
