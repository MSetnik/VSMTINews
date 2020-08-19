package com.example.vsmtiinfo.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsmtiinfo.Activity.Kontakt;
import com.example.vsmtiinfo.Activity.MainActivity;
import com.example.vsmtiinfo.Activity.PocetniActivity;
import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Adapter.StudijskiProgramiAdapter;
import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.Model.Predmet;
import com.example.vsmtiinfo.Model.Semestar;
import com.example.vsmtiinfo.Model.Studij;
import com.example.vsmtiinfo.Model.StudijData;
import com.example.vsmtiinfo.Model.StudijskiProgram;
import com.example.vsmtiinfo.Model.StudijskiProgrami;
import com.example.vsmtiinfo.Model.StudijskiSmjer;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.example.vsmtiinfo.WaitForJson;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class StudijskiProgramiFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyViewModel viewModel;
    private StudijskiProgrami studijskiProgrami;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int clickedItemID = 0;
    private static final String TAG = "MyApp";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_studijski_programi, container, false);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Dohvaćanje studijskih programa ..");
        progressDialog.show();
        viewModel.SetOnStudijskiProgramiFinishListener(new WaitForJson() {
            @Override
            public void GetStudijskiProgrami(StudijskiProgrami SP) {

                Log.d(TAG, "GetStudijskiProgrami: " + SP.getStudijskiProgram());
                recyclerView = viewGroup.findViewById(R.id.recyclerView);
                StudijskiProgramiAdapter studijskiProgramiAdapter = new StudijskiProgramiAdapter(getActivity(), SP.getStudijskiProgram());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(studijskiProgramiAdapter);
                progressDialog.hide();
            }
        });
        NavigationViewSetup();

        return viewGroup;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.vijesti);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.studijskiProg);

    }

    private void NavigationViewSetup()
    {
        drawer = getActivity().findViewById(R.id.drawer_layout);
        navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.studijskiProg);
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
                switch (clickedItemID)
                {
                    case R.id.pocetna:
                        Intent intent = new Intent(getActivity(), PocetniActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.vijesti:
                        Intent intent2 = new Intent(getActivity(), MainActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);

                        break;

                    case R.id.studijskiProg:
                        drawer.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.kontakt:
                        Intent intentKontakt = new Intent(getActivity(), Kontakt.class);
                        intentKontakt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentKontakt);
                        break;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}