package com.example.vsmtiinfo.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.vsmtiinfo.Data.GetNewsData;
import com.example.vsmtiinfo.Data.GetSelectedNewsDetails;
import com.example.vsmtiinfo.GetNewsDetailInterface;
import com.example.vsmtiinfo.GetNewsInterface;
import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.Model.Predmet;
import com.example.vsmtiinfo.Model.StudijskiProgram;
import com.example.vsmtiinfo.Model.StudijskiProgrami;
import com.example.vsmtiinfo.WaitForNews;
import com.example.vsmtiinfo.WaitForNewsDetails;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {
    private static final String TAG = "MyApp";
    private WaitForNews waitForNews;
    private WaitForNewsDetails waitForNewsDetails;
    private GetNewsData getNewsData;
    private GetSelectedNewsDetails getSelectedNewsDetails;
    private NewsDetail newsDetail;
    public ArrayList<News>lNewsVM = new ArrayList<>();

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

    public StudijskiProgrami LoadJsonStudijskiProgrami()
    {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("VSMTI_studijskiProgrami.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        StudijskiProgrami studijskiProgrami = ParseJsonStudijskiProgrami(json);

        return studijskiProgrami;
    }

    public StudijskiProgrami ParseJsonStudijskiProgrami(String json)
    {
        Gson gson = new Gson();
        StudijskiProgrami studijskiProgrami = gson.fromJson(json,StudijskiProgrami.class);
        return studijskiProgrami;
    }


    public void SetGodinaList(ArrayList<Godina>lGodina)
    {
        this.lGodina = lGodina;
    }

    public ArrayList<Godina> GetGodinaList()
    {
        return this.lGodina;
    }



}
