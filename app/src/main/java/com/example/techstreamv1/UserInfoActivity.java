package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity {

    // Static sample data (replace with Firebase values later)
    String[][] userInfo = {
            {"Username", "Krishan"},
            {"Email", "krishan@gmail.com"},
            {"Phone", "+94 76 123 1231"},
            {"Address", "Homagama, Colombo Sri Lanka"},
            {"Date of Birth", "January 10, 2000"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Populate info rows
        for (String[] field : userInfo) {
            String key = field[0];
            String value = field[1];
            int viewId = getResources().getIdentifier("row" + key.replace(" ", ""), "id", getPackageName());
            ViewGroup row = findViewById(viewId);
            if (row != null) {
                TextView label = row.findViewById(R.id.labelText);
                TextView val = row.findViewById(R.id.valueText);
                label.setText(key);
                val.setText(value);
            }
        }

        // Back button
        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());

        // Sign Out
        Button btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(v -> {
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            // Navigate to login if needed
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Edit Profile
        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserInfoEditActivity.class);
            startActivity(intent);
        });
    }
}
