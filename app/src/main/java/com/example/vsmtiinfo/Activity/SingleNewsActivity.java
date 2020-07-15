package com.example.vsmtiinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForNewsDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleNewsActivity extends AppCompatActivity {
    private MyViewModel viewModel;
    private static final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__news);
        MainActivity mainActivity = new MainActivity();
        viewModel = mainActivity.viewModel;


        GetSelectedNewsDetails();
    }

    private void GetSelectedNewsDetails()
    {
        Intent intent = getIntent();
        NewsDetail newsDetail = (NewsDetail) intent.getSerializableExtra("newsDetails");

        ImageView newsImg = findViewById(R.id.newsContentImg);

        if (newsDetail != null) {
            Picasso.get().load(newsDetail.getUrlToImage()).fit().centerCrop().into(newsImg);
        }

        TextView newsTitle = findViewById(R.id.newsContentTitle);
        TextView newsContent = findViewById(R.id.newsContent);

        newsTitle.setText(newsDetail.getNewsTitle());
        String sContent="";
        for(String content : newsDetail.getNewsBigContent())
        {
            sContent = sContent + content + "\n\n";
        }

        newsContent.setText(sContent);

    }
}