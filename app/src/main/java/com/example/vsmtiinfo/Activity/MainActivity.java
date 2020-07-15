package com.example.vsmtiinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForNews;
import com.example.vsmtiinfo.WaitForNewsDetails;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MyApp";
    private ArrayList<News> lNews = new ArrayList<>();
    public MyViewModel viewModel;
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter recyclerViewAdapter;
    private WaitForNews waitForNews;
    public ProgressDialog progressDialog;
    private ArrayList<NewsDetail>AllnewsDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);
        viewModel.GetVSMTINews();

        LoadNews();
    }

    private void LoadNews()
    {
        viewModel.SetOnNewsFinishListener(new WaitForNews() {
            @Override
            public void GetNews(ArrayList<News> lNews) {
//                Log.d(TAG, "GetNews: " + lNews.size());
                RecyclerViewBind(lNews);
                LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimation);
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);
            }
        });


    }


    private void RecyclerViewBind(ArrayList<News> lNews)
    {
        recyclerView = findViewById(R.id.newsRecyclerView);
        recyclerViewAdapter = new NewsRecyclerViewAdapter(getApplicationContext(), lNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
        GetItemClicked();
    }

    private void GetItemClicked()
    {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        recyclerViewAdapter.SetOnClickListener(new NewsRecyclerViewAdapter.OnClickInterface() {

            @Override
            public void OnClickListener(News news) {
                progressDialog.setMessage("Dohvaćanje vijesti ..");
                progressDialog.show();

                viewModel.GetSingleNewsDetails(news.getUrlToNews());
                viewModel.SetOnSingleNewsFinishListener(new WaitForNewsDetails() {
                    @Override
                    public void GetNewsDetails(NewsDetail newsDetail) {
                        Intent intent = new Intent(getApplicationContext(), SingleNewsActivity.class);
                        intent.putExtra("newsDetails", newsDetail);
                        startActivity(intent);
                        progressDialog.dismiss();
                    }
                });

            }

        });
    }


}