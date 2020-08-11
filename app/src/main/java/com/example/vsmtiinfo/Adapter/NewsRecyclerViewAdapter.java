package com.example.vsmtiinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsmtiinfo.Model.News;
import com.example.vsmtiinfo.Model.NewsDetail;
import com.example.vsmtiinfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>{
   private ArrayList<News> lNews;
   private ArrayList<NewsDetail> lNewsdetails;
   private Context context;
   private OnClickInterface onClickInterface;

    public NewsRecyclerViewAdapter(Context context, ArrayList<News>lnews)
    {
        this.lNews = lnews;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_cardview,parent, false);
        NewsViewHolder holder = new NewsViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = lNews.get(position);

        Picasso.get().load(news.getUrlToImage()).into(holder.newsImg);
        holder.newsTitle.setText(news.getTitle());
        holder.newsDate.setText(news.getDate());
        holder.newsContent.setText(news.getContent());

    }

    @Override
    public int getItemCount() {
        return lNews.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImg;
        TextView newsTitle;
        TextView newsDate;
        TextView newsContent;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImg = itemView.findViewById(R.id.newsImg);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDate = itemView.findViewById(R.id.newsDate);
            newsContent = itemView.findViewById(R.id.newsContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickInterface.OnClickListener(lNews.get(position));
                }
            });
        }
    }

    public interface OnClickInterface{
        void OnClickListener(News news);
    }

    public void SetOnClickListener(OnClickInterface onClickInterface)
    {
        this.onClickInterface = onClickInterface;
    }
}
