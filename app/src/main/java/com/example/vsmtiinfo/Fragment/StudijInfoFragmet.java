package com.example.vsmtiinfo.Fragment;

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
        TextView studijNaslov = viewGroup.findViewById(R.id.studijNaslov);
        TextView trajanjeStudijaNaslovTV = viewGroup.findViewById(R.id.trajanjeStudijaNaslov);
        TextView trajanjeStudijaTxtTV = viewGroup.findViewById(R.id.trajanjeStudijaTxt);
        TextView strucniNazivNaslovTV = viewGroup.findViewById(R.id.struciNazivNaslov);
        TextView strucniNazivTxtTV = viewGroup.findViewById(R.id.struciNazivTxt);
        TextView kompetencijeNaslovTV = viewGroup.findViewById(R.id.kompetencijeNaslov);
        TextView kompetencijeTxtTV = viewGroup.findViewById(R.id.kompetencijeTxtTV);
//        kompetencijeRecycler = viewGroup.findViewById(R.id.kompetencijeRecycler);
        TextView ishodiNaslovTV = viewGroup.findViewById(R.id.ishodiUcenjaNaslov);
//        RecyclerView ishodiRecycler = viewGroup.findViewById(R.id.ishodiUcenjaRecycler);
        TextView ishodiTxt = viewGroup.findViewById(R.id.ishodiUcenjaTxtTV);
        TextView predmetiNaslovTV = viewGroup.findViewById(R.id.predmetiNaslov);
//        RecyclerView predmetiRecycler = viewGroup.findViewById(R.id.predmetiRecycler);
        Button btnPredmeti = viewGroup.findViewById(R.id.btnPredmeti);
//        TableLayout predmetiTable = (TableLayout) viewGroup.findViewById(R.id.predmetiTable);
//        FrameLayout predmetiFragment = viewGroup.findViewById(R.id.predmetiFragment);

        final String sStudij;
        sStudij = getArguments().getString("Studij");

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



                        ArrayList<Godina>lGodina = studijData.getPredmeti();

                        for (Godina godina : lGodina) {
                            Godina godina1;
                            Semestar semestar1;
                            Predmet predmet1;

                            ArrayList<Semestar>lSemestar;
                            lSemestar = godina.getSemestar();
                            ArrayList<Semestar>lSemestar1= new ArrayList<>();
                            for (Semestar semestar : lSemestar) {
                                ArrayList<Predmet> lPredmet;
                                lPredmet = semestar.getPredmet();

                                ArrayList<Predmet>lPredmeti1 = new ArrayList<>();
                                for (Predmet predmet : lPredmet)
                                {
                                    predmet1 = new Predmet(predmet.getNazivPredmeta(), predmet.getPredavanja(), predmet.getSeminari(), predmet.getVjezbe(),predmet.getLaboratorijskeVjezbe(), predmet.getKonstrukcijskeVjezbe(), predmet.getEcts(), predmet.getObavezan_izborni());
                                    lPredmeti1.add(predmet1);
                                }
                                semestar1 = new Semestar(semestar.getSemestar(), lPredmeti1);
                                lSemestar1.add(semestar1);

                            }

                            godina1 = new Godina(godina.getGodina(), lSemestar1);
                            this.lGodina.add(godina1);
                        }
//                        GodinaRecyclerAdapter recyclerViewAdapter = new GodinaRecyclerAdapter(getActivity(),lGodina);
//                        holder.predmetiRecycler.setLayoutManager(new LinearLayoutManager(context));
//                        holder.predmetiRecycler.setAdapter(recyclerViewAdapter);
//                        for (Godina godina : lGodina)
//                        {
//                            PredmetRecyclerAdapter recyclerViewAdapter = new PredmetRecyclerAdapter(getActivity(),godina.getPredmet());
//                            holder.predmetiRecycler.setLayoutManager(new LinearLayoutManager(context));
//                            holder.predmetiRecycler.setAdapter(recyclerViewAdapter);
//                            TableRow rowGodina= new TableRow(getActivity());
//                            TableRow.LayoutParams lpGodina = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT );
//                            lpGodina.setMargins(0,0,0,5);
//                            rowGodina.setLayoutParams(lpGodina);
//                            rowGodina.setGravity(Gravity.CENTER);
//
//                            TextView predmetiGodina = new TextView(getActivity());
//                            predmetiGodina.setText(godina.getGodina());
//                            predmetiGodina.setTextSize(16);
//                            predmetiGodina.setTypeface(Typeface.DEFAULT_BOLD);
//
//                            rowGodina.addView(predmetiGodina);
//                            predmetiTable.addView(rowGodina);

//                            ArrayList<Semestar>lSemestar;
//                            lSemestar = godina.getSemestar();
//
//                            LayoutInflater inflater = LayoutInflater.from(getActivity());
//                            ConstraintLayout options_layout = (ConstraintLayout) viewGroup.findViewById(R.id.semestarRow);
//                            for (Semestar semestar : lSemestar)
//                            {
//
//                                View semestarLayout = inflater.inflate(R.layout.godina_layout, options_layout,false);
//
//                                    TextView text = (TextView) semestarLayout.findViewById(R.id.predmetSemestar);
//                                    text.setText(semestar.getSemestar());
//                                    text.setTypeface(Typeface.DEFAULT_BOLD);
//                                    options_layout.addView(semestarLayout);


//                                TableRow rowSemestar= new TableRow(getActivity());
//                                TableRow rowHeaderPredmet = new TableRow(getActivity());
//                                TableRow.LayoutParams lpSemestar = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT );
//                                lpSemestar.setMargins(0,0,0,5);
//                                rowSemestar.setLayoutParams(lpSemestar);
//                                rowSemestar.setGravity(Gravity.CENTER);
//                                rowHeaderPredmet.setLayoutParams(lpSemestar);
//
//                                TextView predmetiSemestarH = new TextView(getActivity());
//                                predmetiSemestarH.setText(semestar.getSemestar());
//                                predmetiSemestarH.setTextSize(14);
//                                predmetiSemestarH.setTypeface(Typeface.DEFAULT_BOLD);
//
//                                TextView predmetNazivH = new TextView(getActivity());
//                                predmetNazivH.setText("Predmet");
//                                predmetNazivH.setTextSize(14);
//                                predmetNazivH.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//
//                                TextView predmetPredavanjaH = new TextView(getActivity());
//                                predmetPredavanjaH.setText("P");
//                                predmetPredavanjaH.setTextSize(14);
//                                predmetPredavanjaH.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//                                TextView predmetSeminariH = new TextView(getActivity());
//                                predmetSeminariH.setText("S");
//                                predmetSeminariH.setTextSize(14);
//                                predmetSeminariH.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//                                TextView predmetVjezbeH = new TextView(getActivity());
//                                predmetVjezbeH.setText("V");
//                                predmetVjezbeH.setTextSize(14);
//                                predmetVjezbeH.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//                                TextView predmetECTSH = new TextView(getActivity());
//                                predmetECTSH.setText("ECTS");
//                                predmetECTSH.setTextSize(14);
//                                predmetECTSH.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
////                                    TextView predmetLV = new TextView(getActivity());
////                                    predmetLV.setText(predmet.getLaboratorijskeVjezbe());
////                                    predmetLV.setTextSize(14);
////
////                                    TextView predmetKV = new TextView(getActivity());
////                                    predmetKV.setText(predmet.getKonstrukcijskeVjezbe());
////                                    predmetKV.setTextSize(14);
//
//                                TextView predmetOIH = new TextView(getActivity());
//                                predmetOIH.setText("O/I");
//                                predmetOIH.setTextSize(14);
//                                predmetOIH.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//
//
//                                rowSemestar.addView(predmetiSemestarH);
//                                rowHeaderPredmet.addView(predmetNazivH);
//                                rowHeaderPredmet.addView(predmetPredavanjaH);
//                                rowHeaderPredmet.addView(predmetSeminariH);
//                                rowHeaderPredmet.addView(predmetVjezbeH);
//                                rowHeaderPredmet.addView(predmetECTSH);
//                                rowHeaderPredmet.addView(predmetOIH);
//                                predmetiTable.addView(rowSemestar);
//                                predmetiTable.addView(rowHeaderPredmet);


//                                ArrayList<Predmet>lPredmet;
//                                lPredmet = semestar.getPredmet();
//
//                                for (Predmet predmet : lPredmet)
//                                {
//                                    TableRow rowPredmet= new TableRow(getActivity());
//                                    TableRow.LayoutParams lpPredmet = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT );
//                                    lpPredmet.weight = 1.0f;
//                                    lpPredmet.setMargins(0,0,0,5);
//                                    rowPredmet.setLayoutParams(lpPredmet);
//
//                                    ViewGroup.LayoutParams lpViewG = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                                    TextView predmetNaziv = new TextView(getActivity());
//                                    predmetNaziv.setText(predmet.getNazivPredmeta());
//                                    predmetNaziv.setTextSize(14);
//
//                                    TextView predmetPredavanja = new TextView(getActivity());
//                                    predmetPredavanja.setText(String.valueOf(predmet.getPredavanja()));
//                                    predmetPredavanja.setTextSize(14);
//
//                                    TextView predmetSeminari = new TextView(getActivity());
//                                    predmetSeminari.setText(String.valueOf(predmet.getSeminari()));
//                                    predmetSeminari.setTextSize(14);
//
//                                    TextView predmetVjezbe = new TextView(getActivity());
//                                    predmetVjezbe.setText(String.valueOf(predmet.getVjezbe()));
//                                    predmetVjezbe.setTextSize(14);
//
//                                    TextView predmetECTS = new TextView(getActivity());
//                                    predmetECTS.setText(String.valueOf(predmet.getEcts()));
//                                    predmetECTS.setTextSize(14);
//
////                                    TextView predmetLV = new TextView(getActivity());
////                                    predmetLV.setText(predmet.getLaboratorijskeVjezbe());
////                                    predmetLV.setTextSize(14);
////
////                                    TextView predmetKV = new TextView(getActivity());
////                                    predmetKV.setText(predmet.getKonstrukcijskeVjezbe());
////                                    predmetKV.setTextSize(14);
//
//                                    TextView predmetOI = new TextView(getActivity());
//                                    predmetOI.setText(String.valueOf(predmet.getObavezan_izborni()));
//                                    predmetOI.setTextSize(14);
//
//                                    rowPredmet.addView(predmetNaziv);
//                                    rowPredmet.addView(predmetPredavanja);
//                                    rowPredmet.addView(predmetSeminari);
//                                    rowPredmet.addView(predmetVjezbe);
//                                    rowPredmet.addView(predmetECTS);
//                                    rowPredmet.addView(predmetOI);
//                                    predmetiTable.addView(rowPredmet);
//                                }
//
//                            }
//                        }

                    }
                }
            }
        }

//        PredmetiFragment predmetiFragment1 = new PredmetiFragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("lGodina",  lGodina);
//        bundle.putString("Studij", sStudij);
//        predmetiFragment1.setArguments(bundle);
//
//        FragmentManager fm = getFragmentManager();
//        assert fm != null;
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.predmetiFragment,predmetiFragment1);
//        fragmentTransaction.commit();

        btnPredmeti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.SetGodinaList(lGodina);
                Intent intent = new Intent(getActivity(), StudijPredmetiActivity.class);
                intent.putExtra("Studij", sStudij);
                startActivity(intent);
            }
        });
    }

}