package com.example.techstreamv1;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techstreamv1.R;

public class ActivityUserInfo extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userIdTextView;
    private TextView aboutTextView;
    private Button exitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize views
        userNameTextView = findViewById(R.id.userNameTextView);
        userIdTextView = findViewById(R.id.userIdTextView);
        aboutTextView = findViewById(R.id.aboutTextView);
        exitButton = findViewById(R.id.exitButton);

        // Set data
        userNameTextView.setText("Krishan Subramaniyam");
        userIdTextView.setText("2020T00886");
        aboutTextView.setText("This app was developed by Krishan Subramaniyam (ID:2020T00886) as part of an academic project under the Mobile Application Development course. Titled TechStream, the app aims to centralize and streamline important updates within the Faculty of Technology. It delivers real-time information including academic announcements, faculty events, and sports highlights all in a single, easy-to-navigate platform. With its clean interface and intuitive features such as categorized news feeds and an integrated to-do list, TechStream ensures that both students and staff remain informed, productive, and connected.");

        // Set exit button functionality
        exitButton.setOnClickListener(v -> {
            // Handle exit button click - typically finish the activity or navigate back
            finish();
        });
    }
}
