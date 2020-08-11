package com.example.vsmtiinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class StudijPredmetiActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private MyViewModel viewModel;
    private ArrayList<Godina> lGodina= new ArrayList<>();
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
        String odabraniStudij = intent.getStringExtra("Studij");
        StudijskiProgrami studijskiProgrami = viewModel.LoadJsonStudijskiProgrami();

        final ArrayList<StudijskiProgram>lSProg;

        lSProg = studijskiProgrami.getStudijskiProgram();
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
                        ArrayList<Godina> lGodina = studijData.getPredmeti();

                        for (Godina godina : lGodina) {
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
                            this.lGodina.add(godina1);
                        }
                    }
                }
            }
        }


        GodinaRecyclerAdapter recyclerAdapter = new GodinaRecyclerAdapter(getApplicationContext(), lGodina);
        RecyclerView godinaRecycler = findViewById(R.id.recyclerGodina);
        godinaRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        godinaRecycler.setAdapter(recyclerAdapter);
    }
}