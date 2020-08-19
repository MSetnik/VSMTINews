package com.example.vsmtiinfo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.customView.CustomScrollView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class Kontakt extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int clickedItemID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontakt);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ToolbarSetup();
        NavigationViewSetup();
        OpenMailApp();
        OpenPhoneApp();
        OpenVsmtiLink();
    }

    private void NavigationViewSetup() {
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.kontakt);
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
                switch (clickedItemID) {
                    case R.id.pocetna:
                        Intent intent = new Intent(Kontakt.this, PocetniActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.vijesti:
                        Intent intent2 = new Intent(Kontakt.this, MainActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);

                        break;

                    case R.id.studijskiProg:
                        Intent intent3 = new Intent(Kontakt.this, MainActivity.class);
                        intent3.putExtra("fragment", "studijski programi");
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        break;

                    case R.id.kontakt:
                        drawer.closeDrawer(GravityCompat.START);
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
        navigationView.setCheckedItem(R.id.kontakt);
    }

    private void ToolbarSetup() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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

    private void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void OpenPhoneApp() {
        ConstraintLayout phone = findViewById(R.id.constraintPhoneNumber);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "033721099", null));
                startActivity(intent);
            }
        });
    }

    private void OpenVsmtiLink()
    {
        ConstraintLayout vsmtiLink = findViewById(R.id.constraintWeb);
        TextView vsmtiurl = findViewById(R.id.TVWebLink);
        vsmtiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(vsmtiurl.getText().toString());
            }
        });
    }

    private void OpenMailApp()
    {
        ConstraintLayout mail = findViewById(R.id.constraintMail);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "info@vsmti.hr"); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Upit");
                startActivity(Intent.createChooser(emailIntent,"Kontakt"));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng vsmti = new LatLng(45.841861, 17.387163);
        mMap.addMarker(new MarkerOptions().position(vsmti).title("Visoka škola za menadžment u turizmu i informatici u Virovitici"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(vsmti,15.0f));

        CustomScrollView customScrollView = findViewById(R.id.ScrollView);

        customScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                customScrollView.setEnableScrolling(true);
                return false;
            }
        });

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                customScrollView.setEnableScrolling(false);
            }
        });
    }
    
}