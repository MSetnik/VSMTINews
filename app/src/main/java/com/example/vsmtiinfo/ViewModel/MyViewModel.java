package com.example.vsmtiinfo.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.vsmtiinfo.Data.GetNewsData;
import com.example.vsmtiinfo.Data.GetSelectedNewsDetails;
import com.example.vsmtiinfo.GetNewsDetailInterface;
import com.example.vsmtiinfo.GetNewsInterface;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.WaitForNews;
import com.example.vsmtiinfo.WaitForNewsDetails;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {
    private static final String TAG = "MyApp";
    private  ArrayList<News> lNews = new ArrayList<>();
    private GetNewsInterface getNewsInterface;
    private WaitForNews waitForNews;
    private WaitForNewsDetails waitForNewsDetails;
    private GetNewsData getNewsData;
    private GetSelectedNewsDetails getSelectedNewsDetails;
    private NewsDetail newsDetail;

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


}
