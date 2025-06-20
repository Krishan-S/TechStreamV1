package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class UserInfoEditActivity extends AppCompatActivity {

    private EditText editUsername, editEmail;
    private Button btnEditProfile, btnSignOut, btnSave, btnCancel;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = user.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // Initialize views
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Load user data
        loadUserData();

        // Edit profile button
        btnEditProfile.setOnClickListener(v -> {
            editUsername.setEnabled(true);
            editEmail.setEnabled(true);
        });

        // Save changes
        btnSave.setOnClickListener(v -> saveUserData());

        // Cancel edit
        btnCancel.setOnClickListener(v -> {
            loadUserData();  // Reload data to discard changes
            editUsername.setEnabled(false);
            editEmail.setEnabled(false);
        });

        // Sign out
        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));  // Navigate to Login Page
            finish();
        });
    }

    private void loadUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    editUsername.setText(username != null ? username : "");
                    editEmail.setText(email != null ? email : "");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(UserInfoEditActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData() {
        String newUsername = editUsername.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();

        if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newEmail)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        userRef.child("username").setValue(newUsername);
        userRef.child("email").setValue(newEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        editUsername.setEnabled(false);
                        editEmail.setEnabled(false);
                    } else {
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
