package com.bannerstone.smileynews.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bannerstone.smileynews.R;
import com.bannerstone.smileynews.services.models.Article;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsRecyclerViewAdapter extends
        RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>{

    private Context context;
    private List<Article> articles = Collections.emptyList();

    public NewsRecyclerViewAdapter(List<Article> articles, Context context) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.headlines_content, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final Article article = articles.get(position);
        int id = context.getResources().getIdentifier(article.getImageName() , "drawable", context.getPackageName());
        holder.bindTo(id);
    }

    @Override
    public int getItemCount() {
        return (articles == null || articles.isEmpty()) ? 0 : articles.size();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headline_image)
        ImageView imageView;

        public NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindTo(int imageID) {
            imageView.setImageResource(imageID);
        }
    }
}


