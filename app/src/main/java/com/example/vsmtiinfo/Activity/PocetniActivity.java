package com.example.vsmtiinfo.Activity;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Adapter.PocetniActivityRecyclerViewAdapter;
import com.example.vsmtiinfo.Fragment.NewsFragment;
import com.example.vsmtiinfo.Fragment.StudijskiProgramiFragment;
import com.example.vsmtiinfo.Model.Dokument;
import com.example.vsmtiinfo.Model.Linkovi;
import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.Notification;
import com.example.vsmtiinfo.Model.StudijskiProgram;
import com.example.vsmtiinfo.Notification.NotificationWorker;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.http.HTTP;

public class PocetniActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private DrawerLayout drawer;
    private ArrayList<Linkovi>lLinkovi = new ArrayList<>();
    private RecyclerView recyclerView;
    private  NavigationView navigationView;
    private MyViewModel viewModel;
    private int clickedItemID = 0;
    private PocetniActivityRecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetni);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);
        ToolbarSetup();
        NavigationViewSetup();
        RecyclerViewBind();
        GetNotifications();
        GetDokumenti();
    }

    private void GetNotifications()
    {
        viewModel.SetOnNotificationsListener(new MyViewModel.WaitForNotificationInterface() {
            @Override
            public void GetNotifications(ArrayList<Notification> notification) {
                Log.d(TAG, "GetNotifications: " + notification.size());

                //Koristeno kako bi se zaustavili i uklonili ostali NotificationWorker taskovi prilikom otvaranja activitija iz notifikacije
                WorkManager.getInstance(getApplicationContext()).cancelAllWork();
                WorkManager.getInstance(getApplicationContext()).pruneWork();


                for (Notification notification1 : notification)
                {
                    startNotificationTask(getApplicationContext(),notification1);
                }

            }
        });
    }

    private void GetDokumenti()
    {
        viewModel.SetOnDokumentiFinishListener(new MyViewModel.WaitForDokumentiInterface() {
            @Override
            public void GetDokumenti(ArrayList<Dokument> lDokumenti) {
                Log.d(TAG, "GetDokumenti: " + lDokumenti.size());
            }
        });
    }

    public void startNotificationTask(Context context, Notification no) {
        PeriodicWorkRequest mNotificationWorkRequest;

        WorkManager workManager = WorkManager.getInstance(context);

        Data data = new Data.Builder()
                .putString("naslov", no.getNaslov())
                .putString("text", no.getText())
                .putInt("id", no.getId())
                .build();

        //NOTIFICATION TASK
        mNotificationWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
                .addTag("NotificationWorker")
                .setInputData(data)
                .build();

        // Može se koristiti kao prikaz samo jedne obavijesti
//        workManager.enqueueUniquePeriodicWork("NotificationWorker", ExistingPeriodicWorkPolicy.REPLACE, mNotificationWorkRequest);
        workManager.enqueue(mNotificationWorkRequest);
    }

    private void RecyclerViewBind() {
        lLinkovi.add(new Linkovi("VSMTI vijesti", "null", R.drawable.ic_news));
        lLinkovi.add(new Linkovi("Studijski programi", "null", R.drawable.ic_sprogrami));
        lLinkovi.add(new Linkovi("Dokumenti", "null",R.drawable.ic_documents));
        lLinkovi.add(new Linkovi("Studom", "http://studom.vsmti.hr",R.drawable.circle_croppedstudom));
        lLinkovi.add(new Linkovi("Računarstvo", "http://racunarstvo.vsmti.hr", R.drawable.circle_croppedrac));
        lLinkovi.add(new Linkovi("Loomen", "http://loomen.vsmti.hr", R.drawable.circle_croppedloomen));
        lLinkovi.add(new Linkovi("Erasmus", "https://vsmti.hr/erasmus/", R.drawable.circle_croppederasmus));
        lLinkovi.add(new Linkovi("Office365", "https://outlook.office365.com/owa/?realm=vsmti.hr#path=/mail", R.drawable.circle_croppedo365));
        lLinkovi.add(new Linkovi("Azure", "https://azureforeducation.microsoft.com/devtools",R.drawable.circle_croppedazure));
        lLinkovi.add(new Linkovi("Kivuto", "http://e5.onthehub.com/d.ashx?s=ui4qkso06k",R.drawable.circle_croppedkivuto));
        lLinkovi.add(new Linkovi("Unwto", "http://affiliatemembers.unwto.org/en/affiliate-member-organization/456915", R.drawable.circle_croppedunwto));

       for (Linkovi linkovi : lLinkovi)
       {
           Log.d(TAG, "RecyclerViewBind: " + linkovi.getImageName() + "   " + linkovi.getImg());
       }

        recyclerView = findViewById(R.id.recyclerPocetni);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerViewAdapter = new PocetniActivityRecyclerViewAdapter(this, lLinkovi);
        recyclerView.setAdapter(recyclerViewAdapter);
        IzbornikClick();
        GetClickedItem();
    }

    private void IzbornikClick()
    {
        recyclerViewAdapter.SetOnClickListener(new PocetniActivityRecyclerViewAdapter.OpenLinkInterface() {
            @Override
            public void OpenWebPage(Linkovi linkovi) {
                openWebPage(linkovi.getUrl());
            }
        });
    }

    private void ToolbarSetup()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Izbornik");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navDrawerLink = (TextView) headerView.findViewById(R.id.navDrawerLink);


//        TextView navDrawerLink =(TextView) findViewById(R.id.navDrawerLink);
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
        Log.d(TAG, "openWebPage: " + url);
        try {
            String link="";
            if( URLUtil.isValidUrl(url))
            {
                Uri webpage = Uri.parse(url);
                Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(myIntent);
            }
            else
            {
                link = "https://"+url;
                Uri webpage = Uri.parse(link);
                Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(myIntent);
            }

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            Log.d(TAG, "openWebPage: " + e.getMessage());
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
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.pocetna);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.pocetna:
                        clickedItemID = R.id.pocetna;
                        break;

                    case R.id.vijesti:
                        clickedItemID = R.id.vijesti;
                        break;

                    case R.id.studijskiProg:
                        clickedItemID = R.id.studijskiProg;

                        break;

                    case R.id.kontakt:
                        clickedItemID = R.id.kontakt;
                        break;


                    case R.id.virtualnaSetnja:
                        clickedItemID = R.id.virtualnaSetnja;
                        break;

                }
                //zatvori drawer
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        // dodaje se drawer listener kako bi se spriječio lagg kod zatvaranja drawera

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                switch (clickedItemID)
                {
                    case R.id.pocetna:
                        break;

                    case R.id.vijesti:
                        Intent intent = new Intent(PocetniActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.studijskiProg:
                        Intent intent2 = new Intent(PocetniActivity.this, MainActivity.class);
                        intent2.putExtra("fragment", "studijski programi");
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);

                        break;

                    case R.id.kontakt:
                        Intent intentKontakt = new Intent(PocetniActivity.this, Kontakt.class);
                        intentKontakt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentKontakt);
                        break;


                    case R.id.virtualnaSetnja:
                        Intent intentVS = new Intent(PocetniActivity.this, ProstorSkoleActivity.class);
                        intentVS.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentVS);
                        break;

                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigationView.setCheckedItem(R.id.pocetna);
    }

    private void GetClickedItem()
    {
        recyclerViewAdapter.SetOnClickListener(new PocetniActivityRecyclerViewAdapter.StartClickedcardActivityInterface() {
            @Override
            public void StartActivity(Linkovi linkovi) {
                if(linkovi.getImageName().equals("VSMTI vijesti"))
                {
                    Intent intent = new Intent(PocetniActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(linkovi.getImageName().equals("Studijski programi"))
                {
                    Intent intent = new Intent(PocetniActivity.this, MainActivity.class);
                    intent.putExtra("fragment", "studijski programi");
                    startActivity(intent);
                }
                else if(linkovi.getImageName().equals("Dokumenti"))
                {
                    Intent intent = new Intent(PocetniActivity.this, MainActivity.class);
                    intent.putExtra("fragment", "dokumenti");
                    startActivity(intent);
                }
            }
        });
    }

}