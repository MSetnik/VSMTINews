package com.example.vsmtiinfo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Fragment.NewsFragment;
import com.example.vsmtiinfo.Fragment.StudijskiProgramiFragment;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.Model.StudijskiProgrami;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForJson;
import com.example.vsmtiinfo.WaitForNews;
import com.example.vsmtiinfo.WaitForNewsDetails;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MyApp";
    private ArrayList<News> lNews = new ArrayList<>();
    public MyViewModel viewModel;
    private ArrayList<NewsDetail>AllnewsDetails = new ArrayList<>();
    private StudijskiProgrami studijskiProgrami;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);
        ToolbarSetupBeforeLoading();
        LoadNews();

    }



    private void ToolbarSetupBeforeLoading()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }


    private void ToolbarSetup()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        final TextView navDrawerLink = findViewById(R.id.navDrawerLink);
        navDrawerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(String.valueOf(navDrawerLink.getText()));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void openWebPage(String url) {
        try {
            String link = "https://" + url;
            Uri webpage = Uri.parse(link);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private void NavigationViewSetup()
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.vijesti);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.vijesti:
                        NewsFragment newsFragment = new NewsFragment();

                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("lNews",  lNews);
                        newsFragment.setArguments(bundle);

                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.myFragment,newsFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.studijskiProg:
                        getSupportFragmentManager().beginTransaction().replace(R.id.myFragment, new StudijskiProgramiFragment()).commit();


                        break;

                }
                //zatvori drawer
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    private void SaveNews ( ArrayList<News>lNews)
    {
        this.lNews = lNews;
    }

    private void LoadNews()
    {
        if (isNetworkConnected())
        {
            viewModel.GetVSMTINews();
            viewModel.SetOnNewsFinishListener(new WaitForNews() {
                @Override
                public void GetNews(ArrayList<News> lNews) {
                    SaveNews(lNews);
                    ToolbarSetup();
                    NavigationViewSetup();
                    NewsFragment newsFragment = new NewsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("lNews",  lNews);
                    newsFragment.setArguments(bundle);

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.myFragment,newsFragment);
                    fragmentTransaction.commit();
                    LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimation);
                    lottieAnimationView.cancelAnimation();
                    lottieAnimationView.setVisibility(View.GONE);


                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new NewsFragment()).commit();
                }
            });
        }
        else
        {
            ToolbarSetup();
            NavigationViewSetup();

            LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimation);
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);

            checkForInternet();
        }


    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void checkForInternet()
    {
        final Handler handler = new Handler();
        final int delay = 5000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(isNetworkConnected())
                {
                    LoadNews();
                    handler.removeCallbacksAndMessages(null);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Checking for internet connection", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, delay);
                }

            }
        }, delay);
    }



}