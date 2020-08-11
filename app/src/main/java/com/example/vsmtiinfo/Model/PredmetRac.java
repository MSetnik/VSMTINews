package com.example.vsmtiinfo.Model;

public class PredmetRac {
    private String nazivPredmeta;
    private int predavanja;
    private int laboratorijskeVjezbe;
    private int konstrukcijskeVjezbe;
    private int ects;
    private String obavezan_izborni;

    public PredmetRac(String nazivPredmeta, int predavanja, int laboratorijskeVjezbe, int konstrukcijskeVjezbe, int ects, String obavezan_izborni) {
        this.nazivPredmeta = nazivPredmeta;
        this.predavanja = predavanja;
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
        return laboratorijskeVjezbe;
    }

    public int getVjezbe() {
        return konstrukcijskeVjezbe;
    }

    public int getEcts() {
        return ects;
    }

    public String getObavezan_izborni() {
        return obavezan_izborni;
    }
}
