package com.example.techstreamv1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Profile Icon click to navigate to User Info Page
        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            startActivity(new Intent(ActivityNews.this, UserInfoEditActivity.class));  // Navigate to User Info Page
        });

        // Developer Info link to navigate to Developer Info Page
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView developerInfoLink = findViewById(R.id.developerInfoLink);
        developerInfoLink.setOnClickListener(v -> {
            startActivity(new Intent(ActivityNews.this, ActivityDevInfo.class));  // Navigate to Developer Info Page
        });
    }
}
