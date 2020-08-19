package com.example.vsmtiinfo.Model;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Linkovi {
    private String imageName;
    private String url;
    private Integer img;

    public Linkovi(String imageName, String url, Integer img) {
        this.imageName = imageName;
        this.url = url;
        this.img = img;
    }

    public String getImageName() {
        return imageName;
    }

    public String getUrl() {
        return url;
    }

    public Integer getImg() {
        return img;
    }
}
