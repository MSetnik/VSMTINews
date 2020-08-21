package com.example.vsmtiinfo.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vsmtiinfo.Activity.MainActivity;
import com.example.vsmtiinfo.Activity.SingleNewsActivity;
import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForNews;
import com.example.vsmtiinfo.WaitForNewsDetails;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private MyViewModel viewModel;
    private RecyclerView recyclerView;
    private NewsRecyclerViewAdapter recyclerViewAdapter;
    private static final String TAG = "MyApp";
    private ArrayList<News> lNews = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_news, container, false);
//        LoadNews();
        lNews = GetNewsFromActivity();
        recyclerView = viewGroup.findViewById(R.id.newsRecyclerView);
        recyclerViewAdapter = new NewsRecyclerViewAdapter(getActivity(), lNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        GetItemClicked();
        return viewGroup;
    }


    private ArrayList<News> GetNewsFromActivity()
    {
        ArrayList<News> news;
        Bundle bundle = getArguments();

        news = bundle.getParcelableArrayList("lNews");
        return  news;
    }


    private void GetItemClicked()
    {
        if (((MainActivity) getActivity()).isNetworkConnected())
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            recyclerViewAdapter.SetOnClickListener(new NewsRecyclerViewAdapter.OnClickInterface() {

                @Override
                public void OnClickListener(News news) {
                    progressDialog.setMessage("Dohvaćanje vijesti ..");
                    progressDialog.show();
                    final String urlToNews = news.getUrlToNews();
                    viewModel.GetSingleNewsDetails(news.getUrlToNews());
                    viewModel.SetOnSingleNewsFinishListener(new WaitForNewsDetails() {
                        @Override
                        public void GetNewsDetails(NewsDetail newsDetail) {
                            Intent intent = new Intent(getActivity(), SingleNewsActivity.class);
                            intent.putExtra("newsDetails", newsDetail);
                            intent.putExtra("newsUrl", urlToNews);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                    });

                }

            });
        }
        else
        {
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }

    }



}