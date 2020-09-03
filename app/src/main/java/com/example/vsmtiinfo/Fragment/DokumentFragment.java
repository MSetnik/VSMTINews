package com.example.vsmtiinfo.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vsmtiinfo.Activity.Kontakt;
import com.example.vsmtiinfo.Activity.MainActivity;
import com.example.vsmtiinfo.Activity.PocetniActivity;
import com.example.vsmtiinfo.Activity.ProstorSkoleActivity;
import com.example.vsmtiinfo.Adapter.DokumentRecyclerAdapter;
import com.example.vsmtiinfo.Model.Dokument;
import com.example.vsmtiinfo.R;
import com.example.vsmtiinfo.ViewModel.MyViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DokumentFragment extends Fragment {
    private MyViewModel viewModel;
    private static final String TAG = "MyApp";
    private RecyclerView recyclerView;
    private ArrayList<Dokument>lDoc = new ArrayList<>();
    private DokumentRecyclerAdapter recyclerAdapter;

    private int READ_STORAGE_PERMISSION_CODE=1;
    private int WRITE_STORAGE_PERMISSION_CODE=1;
    private ProgressDialog pDialog;
    public static final int progress_bar_type = 0;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private int clickedItemID = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        lDoc = GetDokumenti();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_dokument, container, false);
        recyclerView = viewGroup.findViewById(R.id.recyclerDokumenti);
        recyclerAdapter = new DokumentRecyclerAdapter(lDoc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        GetClickedDokument();
        NavigationViewSetup();
        return viewGroup;
    }


    private ArrayList<Dokument> GetDokumenti()
    {
        ArrayList<Dokument> lDokumenti = new ArrayList<>();
        Bundle bundle = getArguments();

        lDokumenti = bundle.getParcelableArrayList("lDokumenti");
        Log.d(TAG, "GetDokumenti sadawdas: " + lDokumenti.size());
        return lDokumenti;
    }

    private void GetClickedDokument()
    {
        recyclerAdapter.SetOnDokumentClickListener(new DokumentRecyclerAdapter.WaitForDokumentClickInterface() {
            @Override
            public void GetClickedDokument(Dokument dokument) {
                Log.d(TAG, "GetClickedDokument: " + dokument.getNaslov());
                AlertDialogYesNo(dokument);
            }
        });
    }

    private void AlertDialogYesNo(Dokument dokument)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        CheckForStoragePermission(dokument);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Želite li preuzeti "+dokument.getNaslov()+"?").setPositiveButton("Da", dialogClickListener)
                .setNegativeButton("Ne", dialogClickListener).show();
    }

    private void DownloadDocument(Dokument dokument)
    {
        DownloadManager downloadmanager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(dokument.getUrl());

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(dokument.getNaslov()+".pdf");
        request.setDescription("Downloading");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dokument.getNaslov()+".pdf");
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        downloadmanager.enqueue(request);
    }

    private void CheckForStoragePermission(Dokument dokument)
    {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getActivity(), "Preuzimanje ..", Toast.LENGTH_SHORT).show();
            DownloadDocument(dokument);
        }
        else
        {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Potrebna dozvola")
                    .setMessage("Potrebna dozvola za preuzimanje dokumenta")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        else{
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == WRITE_STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d(TAG, "onRequestPermissionsResult: PERMISSION GRANTED");
            }
            else
            {
                Log.d(TAG, "onRequestPermissionsResult: PERMISSION DENIED");
            }
        }
    }

    private void NavigationViewSetup()
    {
        drawer = getActivity().findViewById(R.id.drawer_layout);
        navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(false);
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
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.myFragment, new StudijskiProgramiFragment()).commit();
                        break;


                    case R.id.kontakt:
                        Intent intentKontakt = new Intent(getActivity(), Kontakt.class);
                        intentKontakt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intentKontakt);
                        break;

                    case R.id.virtualnaSetnja:
                        Intent intentVS = new Intent(getActivity(), ProstorSkoleActivity.class);
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

}