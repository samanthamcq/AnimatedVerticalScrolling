package com.bannerstone.smileynews.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bannerstone.smileynews.R;
import com.bannerstone.smileynews.services.models.Article;
import com.bannerstone.smileynews.viewmodel.HeadlinesViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadlineFragment  extends Fragment {

    private NewsRecyclerViewAdapter newsAdapter;
    private HeadlinesViewModel headlinesViewModel;
    private StartSnapHelper startSnapHelper;

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        headlinesViewModel = ViewModelProviders.of(getActivity()).get(HeadlinesViewModel.class);
        startSnapHelper = new StartSnapHelper();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_headlines, container, false);
        ButterKnife.bind(this, view);

        showArticles(headlinesViewModel.getArticles());

        return view;
    }

    private void showArticles(List<Article> articles) {
        mainRecyclerView.setClipToPadding(false);
        mainRecyclerView.setPadding(0, 0, 0, 0);

        AnimatedLinearLayoutManager layoutManager = new AnimatedLinearLayoutManager(getContext());
        mainRecyclerView.setLayoutManager(layoutManager);

        newsAdapter = new NewsRecyclerViewAdapter(articles, getContext());

        mainRecyclerView.setAdapter(newsAdapter);

        startSnapHelper.attachToRecyclerView(mainRecyclerView);
    }
}
