package com.example.vsmtiinfo.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.Dokument;
import com.example.vsmtiinfo.R;

import java.sql.Array;
import java.util.ArrayList;

public class DokumentRecyclerAdapter extends RecyclerView.Adapter<DokumentRecyclerAdapter.DokumentVH>{
    private ArrayList<Dokument>lDokument = new ArrayList<>();
    private WaitForDokumentClickInterface waitForDokumentClickInterface;

    public DokumentRecyclerAdapter(ArrayList<Dokument>lDokument)
    {
        this.lDokument = lDokument;
    }

    @NonNull
    @Override
    public DokumentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dokumenti_cardview,parent, false);
        DokumentVH holder = new DokumentVH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DokumentVH holder, int position) {
        Dokument dokument = lDokument.get(position);

        holder.naslov.setText(dokument.getNaslov());
        holder.opis.setText(dokument.getOpis());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitForDokumentClickInterface.GetClickedDokument(dokument);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lDokument.size();
    }

    public static class DokumentVH extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView naslov;
        TextView opis;
        public DokumentVH(@NonNull View itemView) {
            super(itemView);

            naslov = itemView.findViewById(R.id.dokumentNaslov);
            opis = itemView.findViewById(R.id.dokumentOpis);
            cardView = itemView.findViewById(R.id.cardDokument);
        }
    }

    public interface WaitForDokumentClickInterface{
        void GetClickedDokument(Dokument dokument);
    }

    public void SetOnDokumentClickListener(WaitForDokumentClickInterface waitForDokumentClickInterface)
    {
        this.waitForDokumentClickInterface = waitForDokumentClickInterface;
    }

}
