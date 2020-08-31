package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("id")
    private int id;
    @SerializedName("naslov")
    private String naslov;
    @SerializedName("text")
    private String text;
    @SerializedName("datum")
    private String datumKreiranja;
    @SerializedName("aktivna")
    private boolean aktivna;

    public Notification(String naslov, String text, String datumKreiranja, boolean aktivan) {
        this.naslov = naslov;
        this.text = text;
        this.datumKreiranja = datumKreiranja;
        this.aktivna = aktivan;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public boolean isAktivan() {
        return aktivna;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivna = aktivan;
    }
}
