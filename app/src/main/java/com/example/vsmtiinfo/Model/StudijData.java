package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudijData {
    @SerializedName("Trajanje studija")
    private String trajanjeStudija;
    @SerializedName("Stručni naziv i akademski stupanj")
    private String strucniNazivIAkademskiStupanj;
    @SerializedName("Kompetencije stečene završetkom studija")
    private ArrayList<String> KompetencijeSteceneZavrsetkomStudija;
    @SerializedName("Ishodi učenja")
    private ArrayList<String> IshodiUcenja;
    @SerializedName("Predmeti")
    private ArrayList<Godina> godina;

    public StudijData(String trajanjeStudija, String strucniNazivIAkademskiStupanj, ArrayList<String> kompetencijeSteceneZavrsetkomStudija, ArrayList<String> ishodiUcenja, ArrayList<Godina> godina) {
        this.trajanjeStudija = trajanjeStudija;
        this.strucniNazivIAkademskiStupanj = strucniNazivIAkademskiStupanj;
        KompetencijeSteceneZavrsetkomStudija = kompetencijeSteceneZavrsetkomStudija;
        IshodiUcenja = ishodiUcenja;
        this.godina = godina;
    }

    public String getTrajanjeStudija() {
        return trajanjeStudija;
    }

    public String getStrucniNazivIAkademskiStupanj() {
        return strucniNazivIAkademskiStupanj;
    }

    public ArrayList<String> getKompetencijeSteceneZavrsetkomStudija() {
        return KompetencijeSteceneZavrsetkomStudija;
    }

    public ArrayList<String> getIshodiUcenja() {
        return IshodiUcenja;
    }

    public ArrayList<Godina> getPredmeti() {
        return godina;
    }
}
