package com.example.vsmtiinfo.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {
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

    protected News(Parcel in) {
        urlToImage = in.readString();
        title = in.readString();
        date = in.readString();
        content = in.readString();
        urlToNews = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(urlToImage);
        parcel.writeString(title);
        parcel.writeString(date);
        parcel.writeString(content);
        parcel.writeString(urlToNews);
    }
}
