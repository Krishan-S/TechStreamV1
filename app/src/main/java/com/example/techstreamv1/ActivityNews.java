package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityNews extends AppCompatActivity {

    ImageView profileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Profile icon click to go to User Info
        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityNews.this, UserInfoEditActivity.class);
            startActivity(intent);
        });

        // Developer footer click to go to Dev Info
        TextView devFooter = findViewById(R.id.devFooter);
        devFooter.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityNews.this, ActivityDevInfo.class);
            startActivity(intent);
        });
    }
}
