package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.StudijskiSmjer;
import com.example.vsmtiinfo.R;

import java.util.ArrayList;

public class StudijskiSmjerAdapter extends RecyclerView.Adapter<StudijskiSmjerAdapter.SmjerViewHolder> {
    private ArrayList<StudijskiSmjer> lStudijskiSmjer = new ArrayList<>();
    private Context context;

    public StudijskiSmjerAdapter(Context context, ArrayList<StudijskiSmjer> studijskiSmjer) {
        this.context = context;
        this.lStudijskiSmjer = studijskiSmjer;
    }

    @NonNull
    @Override
    public SmjerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_studij_recycler,parent, false);
        StudijskiSmjerAdapter.SmjerViewHolder holder = new StudijskiSmjerAdapter.SmjerViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmjerViewHolder holder, int position) {
        StudijskiSmjer studijskiSmjer = lStudijskiSmjer.get(position);

        holder.nazivSmjera.setText(studijskiSmjer.getNazivPrograma());
        StudijAdapter recyclerViewAdapter = new StudijAdapter(context,studijskiSmjer.getStudijskiSmjer());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(recyclerViewAdapter);

        boolean isExpanded = studijskiSmjer.isExpanded();
        holder.recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        if (isExpanded)
        {
            holder.nazivSmjera.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_less,0);
        }
    }

    @Override
    public int getItemCount() {
        return lStudijskiSmjer.size();
    }

    public class SmjerViewHolder extends RecyclerView.ViewHolder {
        private TextView nazivSmjera;
        private RecyclerView recyclerView;
        public SmjerViewHolder(@NonNull View itemView) {
            super(itemView);
            nazivSmjera = itemView.findViewById(R.id.nazivStudijskogSmjera);
            recyclerView = itemView.findViewById(R.id.studijRecyclerView);

            nazivSmjera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StudijskiSmjer StudijskiSmjer = lStudijskiSmjer.get(getAdapterPosition());
                    StudijskiSmjer.setExpanded(!StudijskiSmjer.isExpanded());
                    notifyItemChanged(getAdapterPosition()); // poziva se ponovo on bind metoda
                }
            });
        }
    }
}
