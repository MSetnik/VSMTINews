package com.example.vsmtiinfo.Data;

import com.example.vsmtiinfo.Model.Dokument;
import com.example.vsmtiinfo.Model.StudijskiProgrami;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DokumentiRetrofitApi {
    @GET("mobile_load_documents_json.php")
    Call<ArrayList<Dokument>> GetDokumenti();
}
