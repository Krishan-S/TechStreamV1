package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityNews extends AppCompatActivity {

    private ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news); // Use your News screen layout XML

        // Initialize profile icon
        profileIcon = findViewById(R.id.profileIcon);

        // Set click listener on profile icon
        profileIcon.setOnClickListener(v -> {
            // Navigate to User Info Edit page when the profile icon is clicked
            Intent intent = new Intent(ActivityNews.this, UserInfoEditActivity.class);
            startActivity(intent);
        });
    }
}
