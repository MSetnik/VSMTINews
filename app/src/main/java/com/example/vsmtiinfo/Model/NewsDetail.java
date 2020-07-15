package com.example.vsmtiinfo.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsDetail implements Serializable {
    private String newsTitle;
    private String urlToImage;
    private ArrayList<String> newsBigContent;

    public NewsDetail(String newsTitle, String urlToImage, ArrayList<String> newsBigContent) {
        this.newsTitle = newsTitle;
        this.urlToImage = urlToImage;
        this.newsBigContent = newsBigContent;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public ArrayList<String> getNewsBigContent() {
        return newsBigContent;
    }
}
