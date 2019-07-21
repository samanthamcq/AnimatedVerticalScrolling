package com.bannerstone.smileynews.viewmodel;

import android.app.Application;
import android.util.Log;

import com.bannerstone.smileynews.services.models.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HeadlinesViewModel extends AndroidViewModel {

    private List<Article> articles = new ArrayList<Article>() {
    };

    public HeadlinesViewModel(@NonNull Application application) {
        super(application);
        createArticles();
    }

    public List<Article> getArticles() {
        return articles;
    }

    private void createArticles() {
        for(int i=0; i<=10; i++){
            Article a = new Article("00" + i);
            a.setImageName("image" + i);
            articles.add(a);
        }
    }
}
