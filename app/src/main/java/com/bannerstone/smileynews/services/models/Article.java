package com.bannerstone.smileynews.services.models;

import androidx.annotation.NonNull;

public class Article {

    @NonNull
    private String articleId;
    private String imageName;

    public Article(@NonNull String articleId) {
        this.articleId = articleId;
    }

    public String getArticleId() { return articleId; }
    public String getImageName() { return imageName; }

    public void setArticleId(String id) { this.articleId = id; }
    public void setImageName(String imageName) { this.imageName = imageName; }
}
