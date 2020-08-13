package com.example.vsmtiinfo.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.vsmtiinfo.Activity.StudijPredmetiActivity;
import com.example.vsmtiinfo.Adapter.GodinaRecyclerAdapter;
import com.example.vsmtiinfo.Adapter.PredmetRecyclerAdapter;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudijInfoFragmet extends Fragment {
    private MyViewModel viewModel;
    private ArrayList<Studij>lStudij;
    private RecyclerView godinaRecycler;
    private ArrayList<Godina>lGodina = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_studij_info_fragmet, container, false);
        GetStudijData(viewGroup);
        return viewGroup;
    }

    private void GetStudijData(ViewGroup viewGroup)
    {
        final TextView studijNaslov = viewGroup.findViewById(R.id.studijNaslov);
        TextView trajanjeStudijaNaslovTV = viewGroup.findViewById(R.id.trajanjeStudijaNaslov);
        final TextView trajanjeStudijaTxtTV = viewGroup.findViewById(R.id.trajanjeStudijaTxt);
        TextView strucniNazivNaslovTV = viewGroup.findViewById(R.id.struciNazivNaslov);
        final TextView strucniNazivTxtTV = viewGroup.findViewById(R.id.struciNazivTxt);
        TextView kompetencijeNaslovTV = viewGroup.findViewById(R.id.kompetencijeNaslov);
        final TextView kompetencijeTxtTV = viewGroup.findViewById(R.id.kompetencijeTxtTV);
//        kompetencijeRecycler = viewGroup.findViewById(R.id.kompetencijeRecycler);
        TextView ishodiNaslovTV = viewGroup.findViewById(R.id.ishodiUcenjaNaslov);
//        RecyclerView ishodiRecycler = viewGroup.findViewById(R.id.ishodiUcenjaRecycler);
        final TextView ishodiTxt = viewGroup.findViewById(R.id.ishodiUcenjaTxtTV);
        TextView predmetiNaslovTV = viewGroup.findViewById(R.id.predmetiNaslov);
//        RecyclerView predmetiRecycler = viewGroup.findViewById(R.id.predmetiRecycler);
        final Button btnPredmeti = viewGroup.findViewById(R.id.btnPredmeti);
//        TableLayout predmetiTable = (TableLayout) viewGroup.findViewById(R.id.predmetiTable);
//        FrameLayout predmetiFragment = viewGroup.findViewById(R.id.predmetiFragment);

        final String sStudij;
        sStudij = getArguments().getString("Studij");

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Dohvaćanje studija ..");
        progressDialog.show();
        viewModel.SetOnStudijskiProgramiFinishListener(new WaitForJson() {
            @Override
            public void GetStudijskiProgrami(StudijskiProgrami SP) {
                final ArrayList<StudijskiProgram>lSProg;

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
                            if(studij1.getNazivStudija().equals(sStudij))
                            {
                                StudijData studijData = studij1.getStudijData();
                                studijNaslov.setText(studij1.getNazivStudija().toUpperCase());
                                trajanjeStudijaTxtTV.setText(studijData.getTrajanjeStudija());
                                strucniNazivTxtTV.setText(studijData.getStrucniNazivIAkademskiStupanj());


                                ArrayList<String>lKompetencije = studijData.getKompetencijeSteceneZavrsetkomStudija();
                                String kompetencije = "";
                                for (String s : lKompetencije)
                                {
                                    String pocetak = "";
                                    if (Character.isDigit(s.charAt(0)) && !kompetencije.isEmpty())
                                    {
                                        pocetak = s;
                                        kompetencije += "\n"+ pocetak + "\n\n";
                                    }
                                    else if (Character.isDigit(s.charAt(0)))
                                    {
                                        pocetak = s;
                                        kompetencije += pocetak + "\n\n";
                                    }
                                    else
                                    {
                                        kompetencije = kompetencije + s + "\n";
                                    }
                                }
                                kompetencijeTxtTV.setText(kompetencije);

                                ArrayList<String>lIshodiUcenja = studijData.getIshodiUcenja();

                                String ishodi = "";
                                for (String s : lIshodiUcenja)
                                {
                                    ishodi += "- "+ s + "\n";
                                }

                                ishodiTxt.setText(ishodi);

                            }
                        }
                    }
                }
                progressDialog.dismiss();

                btnPredmeti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), StudijPredmetiActivity.class);
                        intent.putExtra("Studij", sStudij);
                        startActivity(intent);
                    }
                });
            }
        });

    }

}