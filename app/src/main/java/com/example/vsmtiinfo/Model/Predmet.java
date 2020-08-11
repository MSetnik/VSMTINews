package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

public class Predmet {
    @SerializedName("NazivPredmeta")
    private String nazivPredmeta;
    @SerializedName("Predavanja")
    private int predavanja;
    @SerializedName("Seminari")
    private int seminari;
    @SerializedName("Vježbe")
    private int vjezbe;
    @SerializedName("Laboratorijske vježbe")
    private int laboratorijskeVjezbe;
    @SerializedName("Konstrukcijske vježbe")
    private int konstrukcijskeVjezbe;
    @SerializedName("ECTS")
    private int ects;
    @SerializedName("Obavezan/Izborni")
    private String obavezan_izborni;

    public Predmet(String nazivPredmeta, int predavanja, int seminari, int vjezbe, int laboratorijskeVjezbe, int konstrukcijskeVjezbe, int ects, String obavezan_izborni) {
        this.nazivPredmeta = nazivPredmeta;
        this.predavanja = predavanja;
        this.seminari = seminari;
        this.vjezbe = vjezbe;
        this.laboratorijskeVjezbe = laboratorijskeVjezbe;
        this.konstrukcijskeVjezbe = konstrukcijskeVjezbe;
        this.ects = ects;
        this.obavezan_izborni = obavezan_izborni;
    }

    public String getNazivPredmeta() {
        return nazivPredmeta;
    }

    public int getPredavanja() {
        return predavanja;
    }

    public int getSeminari() {
        return seminari;
    }

    public int getLaboratorijskeVjezbe() {
        return laboratorijskeVjezbe;
    }

    public int getKonstrukcijskeVjezbe() {
        return konstrukcijskeVjezbe;
    }

    public int getVjezbe() {
        return vjezbe;
    }

    public int getEcts() {
        return ects;
    }

    public String getObavezan_izborni() {
        return obavezan_izborni;
    }
}
