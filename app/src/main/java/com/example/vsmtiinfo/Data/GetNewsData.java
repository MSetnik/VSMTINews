package com.example.vsmtiinfo.Data;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vsmtiinfo.Activity.MainActivity;
import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.GetNewsInterface;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetNewsData extends AsyncTask<Void, Integer, ArrayList<News>> {
    private static final String TAG = "MyApp";
    private ArrayList<News> lNews = new ArrayList<>();
    private GetNewsInterface getNewsInterface;


    @Override
    protected ArrayList<News> doInBackground(Void... voids) {

        try {

            Document doc = Jsoup.connect("https://vsmti.hr/").get();
            Elements newsDetails = doc.select("article.post");

            for(Element article : newsDetails)
            {
                String urlToImage = article.select("img").attr("src");
                String title = article.select("h4.entry-title").select("a").text();
                String date = article.select("p.meta").select("span:nth-of-type(3)").text();
                String content = article.select("div.recent-posts-content").select("p:nth-of-type(2)").text();
                String urlToNews = article.select("h4.entry-title").select("a").attr("href");

                News news = new News(urlToImage,title,date,content,urlToNews);
                lNews.add(news);
            }

            return lNews;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<News> s) {
        super.onPostExecute(s);
        getNewsInterface.OnTaskCompleted(s);

    }

    public void SetOnNewsDataFinishListener(GetNewsInterface newsInterface)
    {
        this.getNewsInterface = newsInterface;
    }

}
