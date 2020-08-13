package com.example.vsmtiinfo.Data;


import com.example.vsmtiinfo.Model.StudijskiProgrami;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonRetrofitApi {
    @GET("VSMTI_studijskiProgrami.json")
    Call<StudijskiProgrami>GetStudijskiProgrami();
}
