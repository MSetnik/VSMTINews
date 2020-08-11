package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.Predmet;
import com.example.vsmtiinfo.Model.Semestar;
import com.example.vsmtiinfo.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SemestarRecyclerAdapter extends RecyclerView.Adapter<SemestarRecyclerAdapter.SemestarVH> {
    ArrayList<Semestar>lSemestar;
    Context context;
    boolean vjezbeNull = false;
    boolean seminariNull = false;

    public SemestarRecyclerAdapter(Context context, ArrayList<Semestar>lSemestar)
    {
        this.context = context;
        this.lSemestar = lSemestar;
    }

    @NonNull
    @Override
    public SemestarRecyclerAdapter.SemestarVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.semestar_layout,parent, false);
        SemestarRecyclerAdapter.SemestarVH holder = new SemestarRecyclerAdapter.SemestarVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SemestarRecyclerAdapter.SemestarVH holder, int position) {
        Semestar semestar = lSemestar.get(position);
        holder.semestar.setText(semestar.getSemestar());

        ArrayList<Integer>Seminari = new ArrayList<>();
        ArrayList<Integer>Vjezbe = new ArrayList<>();
        for (Predmet predmet : semestar.getPredmet())
        {
            Seminari.add(predmet.getSeminari());
            Vjezbe.add(predmet.getVjezbe());
        }

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
            holder.seminari.setText("LV");
            holder.vjezbe.setText("KV");
        }

        PredmetRecyclerAdapter recyclerViewAdapter = new PredmetRecyclerAdapter(context,semestar.getPredmet());
        holder.predmetiRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.predmetiRecycler.setAdapter(recyclerViewAdapter);

    }

    @Override
    public int getItemCount() {
        return lSemestar.size();
    }

    public class SemestarVH extends RecyclerView.ViewHolder {
        TextView semestar;
        TextView seminari;
        TextView vjezbe;
        RecyclerView predmetiRecycler;
        public SemestarVH(@NonNull View itemView) {
            super(itemView);

            semestar = itemView.findViewById(R.id.predmetiSemestar);
            predmetiRecycler = itemView.findViewById(R.id.recyclerPredmeti);
            seminari = itemView.findViewById(R.id.predmetSeminari);
            vjezbe = itemView.findViewById(R.id.predmetVjezbe);
        }
    }
}
