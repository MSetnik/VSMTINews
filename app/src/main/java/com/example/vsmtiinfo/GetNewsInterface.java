package com.example.vsmtiinfo;

import com.example.vsmtiinfo.Model.News;

import java.util.ArrayList;

public interface GetNewsInterface {
    void OnTaskCompleted(ArrayList<News> lNews);
}
