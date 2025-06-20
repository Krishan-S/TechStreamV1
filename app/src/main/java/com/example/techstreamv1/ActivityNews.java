package com.example.techstreamv1;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityNews extends AppCompatActivity {

    private TextView newsTextView; // Declare TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);  // Set the correct layout

        // Initialize the TextView by linking it to the XML ID
        newsTextView = findViewById(R.id.newsTextView);  // Ensure this ID is correct

        // Sample data to display
        if (newsTextView != null) {
            newsTextView.setText("Welcome to the News Screen! Here is the latest news...");  // Set the text
        } else {
            // Log error or handle the null case
            System.out.println("TextView not found!");
        }
    }
}
