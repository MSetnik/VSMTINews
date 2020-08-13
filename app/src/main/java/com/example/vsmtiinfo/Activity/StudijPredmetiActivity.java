package com.example.vsmtiinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.vsmtiinfo.Adapter.GodinaRecyclerAdapter;
import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.Model.NewsDetail;
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

import java.util.ArrayList;

public class StudijPredmetiActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studij_predmeti);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MyViewModel.class);

        GetData();
    }

    private void GetData()
    {

        Intent intent = getIntent();
        final String odabraniStudij = intent.getStringExtra("Studij");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Dohvaćanje predmetaa ..");
        progressDialog.show();
        viewModel.SetOnStudijskiProgramiFinishListener(new WaitForJson() {
            @Override
            public void GetStudijskiProgrami(StudijskiProgrami SP) {
                final ArrayList<StudijskiProgram>lSProg;
                ArrayList<Godina> lGodina= new ArrayList<>();
                lSProg = SP.getStudijskiProgram();
                for (StudijskiProgram studijskiProgram : lSProg)
                {
                    ArrayList<StudijskiSmjer>Smjer;
                    Smjer = studijskiProgram.getStudijskiSmjer();
                    for (StudijskiSmjer studijskiSmjer : Smjer)
                    {
                        ArrayList<Studij>studij;
                        studij = studijskiSmjer.getStudijskiSmjer();
                        for(Studij studij1 : studij)
                        {
                            if(studij1.getNazivStudija().equals(odabraniStudij)) {
                                StudijData studijData = studij1.getStudijData();
                                ArrayList<Godina> lGodina2 = studijData.getPredmeti();

                                for (Godina godina : lGodina2) {
                                    Godina godina1;
                                    Semestar semestar1;
                                    Predmet predmet1;

                                    ArrayList<Semestar> lSemestar;
                                    lSemestar = godina.getSemestar();
                                    ArrayList<Semestar> lSemestar1 = new ArrayList<>();
                                    for (Semestar semestar : lSemestar) {
                                        ArrayList<Predmet> lPredmet;
                                        lPredmet = semestar.getPredmet();

                                        ArrayList<Predmet> lPredmeti1 = new ArrayList<>();
                                        for (Predmet predmet : lPredmet) {
                                            predmet1 = new Predmet(predmet.getNazivPredmeta(), predmet.getPredavanja(), predmet.getSeminari(), predmet.getVjezbe(), predmet.getLaboratorijskeVjezbe(), predmet.getKonstrukcijskeVjezbe(), predmet.getEcts(), predmet.getObavezan_izborni());
                                            lPredmeti1.add(predmet1);
                                        }
                                        semestar1 = new Semestar(semestar.getSemestar(), lPredmeti1);
                                        lSemestar1.add(semestar1);

                                    }

                                    godina1 = new Godina(godina.getGodina(), lSemestar1);
                                    lGodina.add(godina1);
                                }
                            }
                        }
                    }
                }

                GodinaRecyclerAdapter recyclerAdapter = new GodinaRecyclerAdapter(getApplicationContext(), lGodina);
                RecyclerView godinaRecycler = findViewById(R.id.recyclerGodina);
                godinaRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                godinaRecycler.setAdapter(recyclerAdapter);
                progressDialog.dismiss();
            }
        });


    }
}