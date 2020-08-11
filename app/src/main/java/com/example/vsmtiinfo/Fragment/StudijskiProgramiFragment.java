package com.example.vsmtiinfo.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;


public class StudijskiProgramiFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyViewModel viewModel;
    private StudijskiProgrami studijskiProgrami;
    private static final String TAG = "MyApp";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        studijskiProgrami = viewModel.LoadJsonStudijskiProgrami();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_studijski_programi, container, false);
//        LoadNews();
        recyclerView = viewGroup.findViewById(R.id.recyclerView);
        StudijskiProgramiAdapter studijskiProgramiAdapter = new StudijskiProgramiAdapter(getActivity(), studijskiProgrami.getStudijskiProgram());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(studijskiProgramiAdapter);
        return viewGroup;
    }




//    private void GetStudijskiSmjerObject()
//    {
//        ArrayList<StudijskiProgram>lSProg;
//
//        lSProg = studijskiProgrami.getStudijskiProgram();
//        for (StudijskiProgram studijskiProgram : lSProg)
//        {
//            ArrayList<StudijskiSmjer>Smjer;
//            Smjer = studijskiProgram.getStudijskiSmjer();
//            for (StudijskiSmjer studijskiSmjer : Smjer)
//            {
//                ArrayList<Studij>studij;
//                studij = studijskiSmjer.getStudijskiSmjer();
//                for(Studij studij1 : studij)
//                {
//                    ArrayList<Godina>lGodina;
//                    StudijData studijData = studij1.getStudijData();
//                    lGodina = studijData.getPredmeti();
//
//                    for (Godina godina : lGodina)
//                    {
//                        ArrayList<Semestar>lSemestar;
//                        lSemestar = godina.getSemestar();
//
//                        for (Semestar semestar : lSemestar)
//                        {
//                            ArrayList<Predmet>lPredmet;
//
//                            lPredmet = semestar.getPredmet();
//
//                            for (Predmet predmet : lPredmet)
//                            {
//
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//    }
}