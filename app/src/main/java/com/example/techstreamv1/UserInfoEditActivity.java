package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserInfoEditActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnEditProfile, btnSignOut;
    private TextView usernameValue, emailValue;
    private ImageView backArrow;
    private FrameLayout dialogOverlay;
    private EditText dialogUsername, dialogEmail;
    private Button btnDialogCancel, btnDialogUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Firebase
        mAuth = FirebaseAuth.getInstance();  // Initialize Firebase Authentication
        mDatabase = FirebaseDatabase.getInstance().getReference("users");  // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("users");  // Initialize Firebase Database reference

        // Initialize views
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSignOut = findViewById(R.id.btnSignOut);
        usernameValue = findViewById(R.id.usernameValue);
        emailValue = findViewById(R.id.emailValue);
        backArrow = findViewById(R.id.backArrow);
        dialogOverlay = findViewById(R.id.dialogOverlay);
        dialogUsername = findViewById(R.id.dialogUsername);
        dialogEmail = findViewById(R.id.dialogEmail);
        btnDialogCancel = findViewById(R.id.btnDialogCancel);
        btnDialogUpdate = findViewById(R.id.btnDialogUpdate);

        // Load user info
        loadUserInfo();

        // Back Arrow Click
        backArrow.setOnClickListener(v -> {
            finish(); // Go back to previous activity
        });

        // Edit Profile Button
        btnEditProfile.setOnClickListener(v -> {
            // Show dialog overlay for editing user info
            dialogOverlay.setVisibility(View.VISIBLE);
            dialogUsername.setText(usernameValue.getText().toString());
            dialogEmail.setText(emailValue.getText().toString());
        });

        // Cancel Edit Button
        btnDialogCancel.setOnClickListener(v -> {
            // Hide dialog overlay
            dialogOverlay.setVisibility(View.GONE);
        });

        // Update Edit Button
        btnDialogUpdate.setOnClickListener(v -> {
            String updatedUsername = dialogUsername.getText().toString().trim();
            String updatedEmail = dialogEmail.getText().toString().trim();

            android.util.Log.d("UserInfo", "Attempting to update - Username: " + updatedUsername + ", Email: " + updatedEmail);

            // Validate inputs
            if (updatedUsername.isEmpty()) {
                dialogUsername.setError("Username cannot be empty");
                return;
            }

            if (updatedEmail.isEmpty()) {
                dialogEmail.setError("Email cannot be empty");
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(updatedEmail).matches()) {
                dialogEmail.setError("Please enter a valid email");
                return;
            }

            // Update in Firebase
            updateUserInfo(updatedUsername, updatedEmail);
        });

        // Sign Out Button
        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(UserInfoEditActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Close dialog when clicking outside (but not on the card itself)
        dialogOverlay.setOnClickListener(v -> {
            if (v == dialogOverlay) {
                dialogOverlay.setVisibility(View.GONE);
            }
        });
    }

    private void loadUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();  // Get the current user's ID
            mDatabase.child(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        String username = snapshot.child("username").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        // Update UI with the fetched data
                        usernameValue.setText(username);
                        emailValue.setText(email);
                    } else {
                        // Handle user data not found
                        Toast.makeText(UserInfoEditActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle failure
                    Toast.makeText(UserInfoEditActivity.this, "Failed to load user info", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updateUserInfo(String username, String email) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();  // Get the current user's ID

            // Create a map of updates
            Map<String, Object> updates = new HashMap<>();
            updates.put("username", username);
            updates.put("email", email);

            if (username.isEmpty()) {
                dialogUsername.setError("Username cannot be empty");
                return;
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                dialogEmail.setError("Please enter a valid email");
                return;
            }


            // Update both fields in Firebase
            mDatabase.child(userId).updateChildren(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Update UI on the main thread
                    runOnUiThread(() -> {
                        usernameValue.setText(username);
                        emailValue.setText(email);

                        // Hide the dialog after update
                        dialogOverlay.setVisibility(View.GONE);

                        Toast.makeText(UserInfoEditActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    // Handle failure to update the data
                    runOnUiThread(() -> {
                        Toast.makeText(UserInfoEditActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } else {
            Toast.makeText(UserInfoEditActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

}