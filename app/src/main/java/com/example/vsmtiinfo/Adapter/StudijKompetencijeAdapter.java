package com.example.vsmtiinfo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudijKompetencijeAdapter extends RecyclerView.Adapter<StudijKompetencijeAdapter.KompetencijeVH> {
    ArrayList<String>lKompetencije;

    public StudijKompetencijeAdapter(ArrayList<String>lKompetencije)
    {
        this.lKompetencije = lKompetencije;
    }

    @NonNull
    @Override
    public KompetencijeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.studij_kompetencije,parent, false);
        StudijKompetencijeAdapter.KompetencijeVH holder = new StudijKompetencijeAdapter.KompetencijeVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KompetencijeVH holder, int position) {
        String kompetencije = lKompetencije.get(position);
        holder.kompetencije.setText(kompetencije);
    }

    @Override
    public int getItemCount() {
        return lKompetencije.size();
    }

    public class KompetencijeVH extends RecyclerView.ViewHolder {
        TextView kompetencije;
        public KompetencijeVH(@NonNull View itemView) {
            super(itemView);
            kompetencije = itemView.findViewById(R.id.kompetencije);
        }
    }
}
