package com.bannerstone.smileynews.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.bannerstone.smileynews.R;
import com.bannerstone.smileynews.views.HeadlineFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(savedInstanceState);
    }

    private void setupView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            HeadlineFragment fragment = new HeadlineFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
    }
}
