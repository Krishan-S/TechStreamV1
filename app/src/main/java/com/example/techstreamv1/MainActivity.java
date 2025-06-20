package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigate directly to News Screen (ActivityNews)
        // Inside MainActivity.java
        Intent intent = new Intent(MainActivity.this, ActivityNews.class);
        startActivity(intent);
        finish(); // Close MainActivity so it doesn’t stay in the back stack

    }
}
