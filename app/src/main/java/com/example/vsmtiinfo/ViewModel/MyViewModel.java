package com.example.vsmtiinfo.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.vsmtiinfo.Data.DokumentiRetrofitApi;
import com.example.vsmtiinfo.Data.GetNewsData;
import com.example.vsmtiinfo.Data.GetSelectedNewsDetails;
import com.example.vsmtiinfo.Data.JsonRetrofitApi;
import com.example.vsmtiinfo.Data.NotificationRetrofitApi;
import com.example.vsmtiinfo.GetNewsDetailInterface;
import com.example.vsmtiinfo.GetNewsInterface;
import com.example.vsmtiinfo.Model.Dokument;
import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.Model.Notification;
import com.example.vsmtiinfo.Model.StudijskiProgram;
import com.example.vsmtiinfo.Model.StudijskiProgrami;
import com.example.vsmtiinfo.WaitForJson;
import com.example.vsmtiinfo.WaitForNews;
import com.example.vsmtiinfo.WaitForNewsDetails;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyViewModel extends AndroidViewModel {
    private static final String TAG = "MyApp";
    private WaitForNews waitForNews;
    private WaitForNewsDetails waitForNewsDetails;
    private GetNewsData getNewsData;
    private GetSelectedNewsDetails getSelectedNewsDetails;
    private NewsDetail newsDetail;
    public ArrayList<News>lNewsVM = new ArrayList<>();
    private WaitForJson waitForJson;
    private WaitForNotificationInterface waitForNotificationInterface;
    private WaitForDokumentiInterface waitForDokumentiInterface;


    private ArrayList<Godina>lGodina = new ArrayList<>();

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    public void GetVSMTINews()
    {
        getNewsData = new GetNewsData();
        getNewsData.execute();

        getNewsData.SetOnNewsDataFinishListener(new GetNewsInterface() {
            @Override
            public void OnTaskCompleted(ArrayList<News> lNews) {
                waitForNews.GetNews(lNews);
                lNewsVM = lNews;
            }
        });
    }

    public void GetSingleNewsDetails(String urlToSelectedNews)
    {
        getSelectedNewsDetails = new GetSelectedNewsDetails();
        getSelectedNewsDetails.execute(urlToSelectedNews);
        getSelectedNewsDetails.SetOnFinishListener(new GetNewsDetailInterface() {
            @Override
            public void OnTaskCompleted(NewsDetail newsDetail) {
                waitForNewsDetails.GetNewsDetails(newsDetail);
            }
        });
    }

    public void SetOnNewsFinishListener(WaitForNews waitForNews)
    {
        this.waitForNews = waitForNews;
    }

    public void SetOnSingleNewsFinishListener(WaitForNewsDetails waitForNewsDetails)
    {
        this.waitForNewsDetails = waitForNewsDetails;
    }


//    public void ParseJsonStudijskiProgrami(String json)
//    {
//        Gson gson = new Gson();
//        StudijskiProgrami studijskiProgrami = gson.fromJson(json,StudijskiProgrami.class);
//        waitForJson.GetStudijskiProgrami(studijskiProgrami);
//    }

    public void SetOnStudijskiProgramiFinishListener(WaitForJson waitForJson)
    {
        this.waitForJson = waitForJson;
        RetrofitStudijskiProgrami();
    }

    public void RetrofitStudijskiProgrami()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://vsmti.hr/wp-content/uploads/2020/08/").addConverterFactory(GsonConverterFactory.create()).build();

        JsonRetrofitApi jsonRetrofitApi = retrofit.create(JsonRetrofitApi.class);

        Call<StudijskiProgrami> call = jsonRetrofitApi.GetStudijskiProgrami();

        call.enqueue(new Callback<StudijskiProgrami>() {
            @Override
            public void onResponse(Call<StudijskiProgrami> call, Response<StudijskiProgrami> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Gson gson = new Gson();
                StudijskiProgrami studijskiProgrami= response.body();
                waitForJson.GetStudijskiProgrami(studijskiProgrami);
            }

            @Override
            public void onFailure(Call<StudijskiProgrami> call, Throwable t) {
                Log.d(TAG, "onFailure: error");
            }

        });
    }


    public void RetrofitNotifications()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://hub.vsmti.hr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NotificationRetrofitApi notificationRetrofitApi = retrofit.create(NotificationRetrofitApi.class);

        Call<ArrayList<Notification>> call = notificationRetrofitApi.GetNotifications();

        call.enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                ArrayList<Notification>lNotifications = new ArrayList<>();
                lNotifications= response.body();
                waitForNotificationInterface.GetNotifications(lNotifications);
            }

            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                Log.d(TAG, "onFailure: error " + t.toString());
            }
        });

    }

    public interface WaitForNotificationInterface{
        void GetNotifications(ArrayList<Notification> notification);
    }


    public void SetOnNotificationsListener(WaitForNotificationInterface waitForNotificationInterface)
    {
        this.waitForNotificationInterface = waitForNotificationInterface;
        RetrofitNotifications();
    }

    public void RetrofitDokumenti()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://hub.vsmti.hr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DokumentiRetrofitApi dokumentiRetrofitApi = retrofit.create(DokumentiRetrofitApi.class);

        Call<ArrayList<Dokument>> call = dokumentiRetrofitApi.GetDokumenti();

        call.enqueue(new Callback<ArrayList<Dokument>>() {
            @Override
            public void onResponse(Call<ArrayList<Dokument>> call, Response<ArrayList<Dokument>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                waitForDokumentiInterface.GetDokumenti(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Dokument>> call, Throwable t) {
                Log.d(TAG, "onFailure: error " + t.toString());
            }
        });


    }


    public interface WaitForDokumentiInterface{
        void GetDokumenti(ArrayList<Dokument> lDokumenti);
    }


    public void SetOnDokumentiFinishListener(WaitForDokumentiInterface waitForDokumentiInterface)
    {
        this.waitForDokumentiInterface = waitForDokumentiInterface;
        RetrofitDokumenti();
    }

}
