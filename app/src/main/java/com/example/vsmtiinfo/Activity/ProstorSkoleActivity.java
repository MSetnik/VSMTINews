package com.example.vsmtiinfo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsmtiinfo.Fragment.NewsFragment;
import com.example.vsmtiinfo.Fragment.StudijskiProgramiFragment;
import com.example.vsmtiinfo.R;
import com.google.android.material.navigation.NavigationView;

public class ProstorSkoleActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private int clickedItemID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prostor_skole);
        ToolbarSetup();
        NavigationViewSetup();
        OpenVirtualwalk();
    }

    private void OpenVirtualwalk()
    {
        CardView cardViewPrizemlje = findViewById(R.id.prizemlje);
        CardView cardViewKat = findViewById(R.id.kat);

        cardViewPrizemlje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prizemljeUrl = "http://193.198.57.183/vsmti360/VSMTIprizemlje.html";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(prizemljeUrl));
                startActivity(browserIntent);
            }
        });

        cardViewKat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String katUrl = "http://193.198.57.183/vsmti360/VSMTIkat.html";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(katUrl));
                startActivity(browserIntent);
            }
        });
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

    private void ToolbarSetup()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Virtualna šetnja");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navDrawerLink = (TextView) headerView.findViewById(R.id.navDrawerLink);

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
            e.printStackTrace();
        }
    }

    private void NavigationViewSetup()
    {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.virtualnaSetnja);
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
                        Intent intent = new Intent(ProstorSkoleActivity.this, PocetniActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.vijesti:
                        Intent intent1 = new Intent(ProstorSkoleActivity.this, MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;

                    case R.id.studijskiProg:
                        Intent intent2 = new Intent(ProstorSkoleActivity.this, MainActivity.class);
                        intent2.putExtra("fragment", "studijski programi");
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);

                        break;

                    case R.id.kontakt:
                        Intent intentKontakt = new Intent(ProstorSkoleActivity.this, Kontakt.class);
                        intentKontakt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentKontakt);
                        break;

                    case R.id.virtualnaSetnja:
                        break;

                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}