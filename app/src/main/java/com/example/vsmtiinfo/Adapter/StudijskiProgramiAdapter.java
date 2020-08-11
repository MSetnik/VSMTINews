package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.StudijskiProgram;
import com.example.vsmtiinfo.R;

import java.util.ArrayList;

public class StudijskiProgramiAdapter  extends RecyclerView.Adapter<StudijskiProgramiAdapter.StudijViewHolder> {
    private ArrayList<StudijskiProgram> lSProgrami = new ArrayList<>();
    private Context context;

    public StudijskiProgramiAdapter (Context context, ArrayList<StudijskiProgram>lSProgrami)
    {
        this.context = context;
        this.lSProgrami = lSProgrami;
    }

    @NonNull
    @Override
    public StudijViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_studijski_smjer,parent, false);
        StudijskiProgramiAdapter.StudijViewHolder holder = new StudijskiProgramiAdapter.StudijViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudijViewHolder holder, int position) {
        StudijskiProgram studijskiProgram = lSProgrami.get(position);

        holder.VrstaStudija.setText(studijskiProgram.getNazivStudijskogPrograma());
        StudijskiSmjerAdapter recyclerViewAdapter = new StudijskiSmjerAdapter(context,studijskiProgram.getStudijskiSmjer());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(recyclerViewAdapter);

        boolean isExpanded = studijskiProgram.isExpanded();
        holder.recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        if (isExpanded)
        {
            holder.VrstaStudija.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_less,0);
        }
    }

    @Override
    public int getItemCount() {
        return lSProgrami.size();
    }

    public class StudijViewHolder extends RecyclerView.ViewHolder {
        private TextView VrstaStudija;
        private RecyclerView recyclerView;

        public StudijViewHolder(@NonNull View itemView) {
            super(itemView);
            VrstaStudija = itemView.findViewById(R.id.vrstaStudijaCardView);
            recyclerView = itemView.findViewById(R.id.studijskiProgramiCardRecyclerView);


            VrstaStudija.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StudijskiProgram studijskiProgram = lSProgrami.get(getAdapterPosition());
                    studijskiProgram.setExpanded(!studijskiProgram.isExpanded());
                    notifyItemChanged(getAdapterPosition()); // poziva se ponovo on bind metoda
                }
            });
        }
    }
}
