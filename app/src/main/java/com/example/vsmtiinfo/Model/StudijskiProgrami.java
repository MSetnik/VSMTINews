package com.example.vsmtiinfo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudijskiProgrami {
    @SerializedName("StudijskiProgrami")
    private ArrayList<StudijskiProgram> studijskiProgram;

    public StudijskiProgrami(ArrayList<StudijskiProgram> studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public ArrayList<StudijskiProgram> getStudijskiProgram() {
        return studijskiProgram;
    }
}
