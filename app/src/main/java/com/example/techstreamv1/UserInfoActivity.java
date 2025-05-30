package com.example.techstreamv1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;

public class UserInfoActivity extends AppCompatActivity {

    String[][] infoData = {
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

        // Optional profile image logic
        ImageView profileImage = findViewById(R.id.profileImage);

        // Assign dynamic data to each include
        for (int i = 0; i < infoData.length; i++) {
            int viewId = getResources().getIdentifier("row" + infoData[i][0].replace(" ", ""), "id", getPackageName());
            ViewGroup rowLayout = findViewById(viewId);
            if (rowLayout != null) {
                TextView label = rowLayout.findViewById(R.id.labelText);
                TextView value = rowLayout.findViewById(R.id.valueText);
                label.setText(infoData[i][0]);
                value.setText(infoData[i][1]);
            }
        }

        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnSignOut = findViewById(R.id.btnSignOut);

        btnEdit.setOnClickListener(v ->
                Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show());

        btnSignOut.setOnClickListener(v ->
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show());
    }
}
