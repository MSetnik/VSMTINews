package com.example.vsmtiinfo.Data;

import com.example.vsmtiinfo.Model.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationRetrofitApi {

    @GET("mobile_load_notifications_json.php")
    Call<ArrayList<Notification>> GetNotifications();
}
