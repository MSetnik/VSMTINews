package com.example.vsmtiinfo.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Activity.MainActivity;
import com.example.vsmtiinfo.Fragment.StudijskiProgramiFragment;
import com.example.vsmtiinfo.Model.Linkovi;
import com.example.vsmtiinfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PocetniActivityRecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<Linkovi> lLinkovi = new ArrayList<>();
    private Context ctx;
    private OpenLinkInterface openLinkInterface;
    private StartClickedcardActivityInterface startClickedcardActivityInterface;
    public PocetniActivityRecyclerViewAdapter(Context context, ArrayList<Linkovi>linkovi)
    {
        this.lLinkovi = linkovi;
        this.ctx = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == 0)
        {
            view = layoutInflater.inflate(R.layout.pocetna_recycler_cardview,parent,false);
            return new PocetniVH(view);
        }
        view = layoutInflater.inflate(R.layout.pocetna_recycler_cardview,parent,false);
        return new korisniLinkoviVH(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Linkovi linkovi = lLinkovi.get(position);

        if (linkovi.getUrl().contains("null"))
        {

            PocetniVH pocetniVH = (PocetniVH) holder;
            pocetniVH.image1.setImageResource(linkovi.getImg());
            pocetniVH.textView.setText(linkovi.getImageName());
        }
        else
        {
            korisniLinkoviVH korisniLinkoviVH = (korisniLinkoviVH) holder;
            Picasso.get().load(linkovi.getImg()).into(korisniLinkoviVH.image1);
            korisniLinkoviVH.textView.setText(linkovi.getImageName());
        }

    }


    @Override
    public int getItemCount() {
        return lLinkovi.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(lLinkovi.get(position).getUrl().toLowerCase().contains("null"))
        {
            return 0;
        }

        return 1;
    }

    public class PocetniVH extends RecyclerView.ViewHolder {

        ImageView image1;
        TextView textView;
        CardView cardView;

        public PocetniVH(@NonNull View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.imageView1);
            textView = itemView.findViewById(R.id.cardText);
            cardView = itemView.findViewById(R.id.cardViewLinkovi);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String cardClicked = lLinkovi.get(position).getImageName();
                    startClickedcardActivityInterface.StartActivity(lLinkovi.get(position));
                }
            });
        }
    }

    public class korisniLinkoviVH extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView image1;
        TextView textView;
        public korisniLinkoviVH(@NonNull View itemView) {
            super(itemView);

            image1 = itemView.findViewById(R.id.imageView1);
            textView = itemView.findViewById(R.id.cardText);
            cardView = itemView.findViewById(R.id.cardViewLinkovi);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    openLinkInterface.OpenWebPage(lLinkovi.get(position));
                }
            });
        }

    }

    public interface OpenLinkInterface
    {
        void OpenWebPage(Linkovi linkovi);
    }

    public void SetOnClickListener(OpenLinkInterface onClickListener)
    {
        this.openLinkInterface = onClickListener;
    }


    public interface StartClickedcardActivityInterface
    {
        void StartActivity(Linkovi linkovi);
    }

    public void SetOnClickListener(StartClickedcardActivityInterface startClickedcardActivityInterface)
    {
        this.startClickedcardActivityInterface = startClickedcardActivityInterface;
    }
}
