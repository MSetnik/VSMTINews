package com.example.vsmtiinfo.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForNewsDetails;
import com.google.android.material.navigation.NavigationView;
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

        ToolbarSetup();
        GetSelectedNewsDetails();
    }

    private void ToolbarSetup()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vijesti");
        setSupportActionBar(toolbar);
    }




    private void GetSelectedNewsDetails()
    {
        Intent intent = getIntent();
        NewsDetail newsDetail = (NewsDetail) intent.getSerializableExtra("newsDetails");
        String urlToNews = intent.getStringExtra("newsUrl");
        ImageView newsImg = findViewById(R.id.newsContentImg);

        if (newsDetail != null) {
            Picasso.get().load(newsDetail.getUrlToImage()).into(newsImg);
        }

        TextView newsTitle = findViewById(R.id.newsContentTitle);
        TextView newsContent = findViewById(R.id.newsContent);
        TextView newsUrl = findViewById(R.id.newsUrl);

        newsTitle.setText(newsDetail.getNewsTitle());
        String sContent="";
        for(String content : newsDetail.getNewsBigContent())
        {
            sContent = sContent + content + "\n\n";
        }

        newsContent.setText(sContent);
        newsUrl.setText(urlToNews);
    }
}