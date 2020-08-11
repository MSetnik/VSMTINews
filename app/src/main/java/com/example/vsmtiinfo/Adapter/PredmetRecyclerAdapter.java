package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.Predmet;
import com.example.vsmtiinfo.R;

import java.util.ArrayList;

public class PredmetRecyclerAdapter extends RecyclerView.Adapter<PredmetRecyclerAdapter.PredmetVH> {
    ArrayList<Predmet>lPredmeti;
    Context context;
    boolean vjezbeNull = false;
    boolean seminariNull = false;

    public PredmetRecyclerAdapter(Context context,ArrayList<Predmet>lPredmeti)
    {
        this.context = context;
        this.lPredmeti = lPredmeti;
    }

    @NonNull
    @Override
    public PredmetVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.predmet_recycler_layout,parent, false);
        PredmetRecyclerAdapter.PredmetVH holder = new PredmetRecyclerAdapter.PredmetVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PredmetVH holder, int position) {
        Predmet predmet = lPredmeti.get(position);

        ArrayList<Integer>Seminari = new ArrayList<>();
        ArrayList<Integer>Vjezbe = new ArrayList<>();

        Seminari.add(predmet.getSeminari());
        Vjezbe.add(predmet.getVjezbe());


        for (Integer sem : Seminari)
        {
            if (sem != 0) {
                seminariNull = true;
                break;
            }
        }


        for (Integer vj : Vjezbe)
        {
            if (vj != 0) {
                seminariNull = true;
                break;
            }
        }

        if (!seminariNull && !vjezbeNull)
        {
            holder.seminari.setText(String.valueOf(predmet.getLaboratorijskeVjezbe()));
            holder.vjezbe.setText(String.valueOf(predmet.getKonstrukcijskeVjezbe()));
        }
        else
        {
            holder.seminari.setText(String.valueOf(predmet.getSeminari()));
            holder.vjezbe.setText(String.valueOf(predmet.getVjezbe()));
        }

        holder.predmetNaziv.setText(predmet.getNazivPredmeta());
        holder.predavanja.setText(String.valueOf(predmet.getPredavanja()));
        holder.ECTS.setText(String.valueOf(predmet.getEcts()));
        holder.OI.setText(predmet.getObavezan_izborni());
    }

    @Override
    public int getItemCount() {
        return lPredmeti.size();
    }

    public class PredmetVH extends RecyclerView.ViewHolder {
        TextView predmetNaziv;
        TextView predavanja;
        TextView seminari;
        TextView vjezbe;
        TextView ECTS;
        TextView OI;

        public PredmetVH(@NonNull View itemView) {
            super(itemView);

            predmetNaziv = itemView.findViewById(R.id.predmetNaziv);
            predavanja = itemView.findViewById(R.id.predmetPredavanja);
            seminari = itemView.findViewById(R.id.predmetSeminari);
            vjezbe = itemView.findViewById(R.id.predmetVjezbe);
            ECTS = itemView.findViewById(R.id.predmetECTS);
            OI = itemView.findViewById(R.id.predmetOI);
        }
    }
}
