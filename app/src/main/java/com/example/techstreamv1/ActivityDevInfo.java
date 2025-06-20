package com.example.techstreamv1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityDevInfo extends AppCompatActivity {

    private ImageView backButton;
    private ImageView profileImage;
    private TextView developerName;
    private TextView studentId;
    private TextView aboutDescription;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info);

        // Initialize views
        backButton = findViewById(R.id.backButton);
        profileImage = findViewById(R.id.profileImage);
        developerName = findViewById(R.id.developerName);
        studentId = findViewById(R.id.studentId);
        aboutDescription = findViewById(R.id.aboutDescription);
        exitButton = findViewById(R.id.exitButton);

        // Set default data
        developerName.setText("Krishan Subramaniyam");
        studentId.setText("2020T00886");
        aboutDescription.setText("This app was developed by Krishan Subramaniyam (ID:2020T00886) as part of an academic project under the Mobile Application Development course. Titled TechStream, the app aims to centralize and streamline important updates within the Faculty of Technology. It delivers real-time information including academic announcements, faculty events, and sports highlights all in a single, easy-to-navigate platform. With its clean interface and intuitive features such as categorized news feeds and an integrated to-do list, TechStream ensures that both students and staff remain informed, productive, and connected.");

        // Set up click listeners
        backButton.setOnClickListener(v -> {
            // Handle back button click (navigate back)
            onBackPressed();
        });

        exitButton.setOnClickListener(v -> {
            // Handle exit button click (close the activity)
            finish();
        });

        // Optional: Set profile image if available
        // Uncomment and set an actual image if needed
        // profileImage.setVisibility(View.VISIBLE);
        // profileImage.setImageResource(R.drawable.profile_picture);
    }
}
