package com.example.techstreamv1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techstreamv1.R;

public class ActivityNews extends AppCompatActivity {

    private EditText searchEditText;
    private ImageView notificationIcon;
    private ImageView sportsIcon;
    private ImageView educationIcon;
    private ImageView calendarIcon;
    private TextView latestHeader;
    private TextView newsTitle;
    private ImageView newsImage;
    private TextView viewCount;
    private TextView newsSource;
    private TextView newsTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        notificationIcon = findViewById(R.id.notificationIcon);
        sportsIcon = findViewById(R.id.sportsIcon);
        educationIcon = findViewById(R.id.educationIcon);
        calendarIcon = findViewById(R.id.calendarIcon);
        latestHeader = findViewById(R.id.latestHeader);
        newsTitle = findViewById(R.id.newsTitle);
        newsImage = findViewById(R.id.newsImage);
        viewCount = findViewById(R.id.viewCount);
        newsSource = findViewById(R.id.newsSource);
        newsTime = findViewById(R.id.newsTime);

        // Example setup for the news content
        newsTitle.setText("Breaking news: New registrations and applications available!");
        viewCount.setText("150");
        newsSource.setText("Dean office");
        newsTime.setText("4 hours ago");

        // Click listener for the search bar
        searchEditText.setOnClickListener(v -> {
            // Handle search click action
            // For example, open a new activity or filter news based on the text input
        });

        // Click listener for the notification icon
        notificationIcon.setOnClickListener(v -> {
            // Handle notification click action
        });

        // Click listeners for the navigation icons (sports, education, calendar)
        sportsIcon.setOnClickListener(v -> {
            // Handle sports category click
        });

        educationIcon.setOnClickListener(v -> {
            // Handle education category click
        });

        calendarIcon.setOnClickListener(v -> {
            // Handle calendar category click
        });
    }
}
