package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.Godina;
import com.example.vsmtiinfo.R;

import java.util.ArrayList;

public class GodinaRecyclerAdapter extends RecyclerView.Adapter<GodinaRecyclerAdapter.GodinaVH> {
    ArrayList<Godina> lGodina;
    Context context;
    private static final String TAG = "MyApp";
    public GodinaRecyclerAdapter(Context context , ArrayList<Godina>lGodina)
    {
        this.context = context;
        this.lGodina = lGodina;
    }
    @NonNull
    @Override
    public GodinaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.godina_layout,parent, false);
        GodinaRecyclerAdapter.GodinaVH holder = new GodinaRecyclerAdapter.GodinaVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GodinaVH holder, int position) {
        Godina godina = lGodina.get(position);
        holder.godina.setText(godina.getGodina());

        Log.d(TAG, "onBindViewHolder: " + godina.getSemestar());

        SemestarRecyclerAdapter recyclerViewAdapter = new SemestarRecyclerAdapter(context,godina.getSemestar());
        holder.recyclerViewSemestar.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewSemestar.setAdapter(recyclerViewAdapter);
    }

    @Override
    public int getItemCount() {
        return lGodina.size();
    }

    public class GodinaVH extends RecyclerView.ViewHolder {
        TextView godina;
        RecyclerView recyclerViewSemestar;
        public GodinaVH(@NonNull View itemView) {
            super(itemView);
            godina = itemView.findViewById(R.id.predmetiGodina);
            recyclerViewSemestar = itemView.findViewById(R.id.recyclerSemestar);
        }
    }
}
