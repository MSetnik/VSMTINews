package com.example.vsmtiinfo.Data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.vsmtiinfo.GetNewsDetailInterface;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.ViewModel.MyViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GetSelectedNewsDetails extends AsyncTask<String, Void, NewsDetail> {
    private static final String TAG = "MyApp";
    private NewsDetail newsDetail;
    private GetNewsDetailInterface getNewsDetailInterface;

    @Override
    protected NewsDetail doInBackground(String... strings) {
        try {

            Document doc = Jsoup.connect(String.valueOf(strings[0])).get();
            String title = doc.title();
            Elements newsDetails = doc.select("section#content");

            for(Element article : newsDetails)
            {
                String urlToImage = article.select("img").attr("src");
                String content = article.select("div.post-content").select("p").toString();//.select("p").text();
                String[] paragraphs = content.split("</p>");

                if(content.isEmpty())
                {
                    content = article.select("div.post-content").select("div:not([id])").select("div:not([class])").toString();//.select("p").text();
                    paragraphs = content.split("</div>");
                }

                String text;
                ArrayList<String>bigcontent = new ArrayList<>();
                for (String paragraph : paragraphs)
                {
                    text = Jsoup.parse(paragraph).text();
                    bigcontent.add(text);

                }

                newsDetail = new NewsDetail(title,urlToImage,bigcontent);
            }

            return newsDetail;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        super.onPostExecute(newsDetail);
        getNewsDetailInterface.OnTaskCompleted(newsDetail);
    }

    public void SetOnFinishListener(GetNewsDetailInterface getNewsDetailInterface)
    {
        this.getNewsDetailInterface = getNewsDetailInterface;
    }

}
