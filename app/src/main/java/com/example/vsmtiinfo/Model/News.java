package com.example.vsmtiinfo.Model;

public class News {
    private String urlToImage;
    private String title;
    private String date;
    private String content;
    private String urlToNews;

    public News(String urlToImage, String title, String date, String content, String urlToNews) {
        this.urlToImage = urlToImage;
        this.title = title;
        this.date = date;
        this.content = content;
        this.urlToNews = urlToNews;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getUrlToNews() {
        return urlToNews;
    }
}
