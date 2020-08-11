package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Fragment.NewsFragment;
import com.example.vsmtiinfo.Fragment.StudijInfoFragmet;
import com.example.vsmtiinfo.Model.Studij;
import com.example.vsmtiinfo.R;

import java.util.ArrayList;

public class StudijAdapter extends RecyclerView.Adapter<StudijAdapter.StudijViewHolder>{
    private ArrayList<Studij>lStudij;
    private Context context;
    private static final String TAG = "MyApp";

    public StudijAdapter(Context context, ArrayList<Studij>lStudij)
    {
        this.lStudij = lStudij;
        this.context = context;
    }
    @NonNull
    @Override
    public StudijViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.studij,parent, false);
        StudijAdapter.StudijViewHolder holder = new StudijAdapter.StudijViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final StudijViewHolder holder, int position) {
        Studij studij = lStudij.get(position);
        holder.studij.setText(studij.getNazivStudija());
        
        holder.studij.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                StudijInfoFragmet studijInfoFragmet = new StudijInfoFragmet();

                Bundle bundle = new Bundle();
                bundle.putString("Studij", String.valueOf(holder.studij.getText()));
                studijInfoFragmet.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.myFragment, studijInfoFragmet).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lStudij.size();
    }

    public class StudijViewHolder extends RecyclerView.ViewHolder {
        private TextView studij;
        public StudijViewHolder(@NonNull View itemView) {
            super(itemView);
            studij = itemView.findViewById(R.id.nazivStudijskogSmjera);
            
        }
    }
}
