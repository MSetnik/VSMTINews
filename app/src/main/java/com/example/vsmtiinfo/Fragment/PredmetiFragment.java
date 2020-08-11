package com.example.vsmtiinfo.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vsmtiinfo.Adapter.GodinaRecyclerAdapter;
import com.example.vsmtiinfo.Adapter.NewsRecyclerViewAdapter;
import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.Model.News;
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

public class PredmetiFragment extends Fragment {
    ArrayList<Godina>lGodina= new ArrayList<>();
    String sStudij="";
    private RecyclerView recyclerView;
    private MyViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_studij_predmeti, container, false);
//        LoadNews();

        GetData(viewGroup);
        recyclerView = viewGroup.findViewById(R.id.recyclerGodina);
        GodinaRecyclerAdapter recyclerViewAdapter = new GodinaRecyclerAdapter(getActivity(), lGodina);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return viewGroup;
    }


    private void GetPredmeti()
    {
        Bundle bundle = getArguments();
        assert bundle != null;
        sStudij = bundle.getString("Studij");
    }

    private void GetData(ViewGroup viewGroup)
    {
        GetPredmeti();
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
                    if(studij1.getNazivStudija().equals(sStudij)) {
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


        GodinaRecyclerAdapter recyclerAdapter = new GodinaRecyclerAdapter(getActivity(), lGodina);
        RecyclerView godinaRecycler =viewGroup.findViewById(R.id.recyclerGodina);
        godinaRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        godinaRecycler.setAdapter(recyclerAdapter);
    }
}