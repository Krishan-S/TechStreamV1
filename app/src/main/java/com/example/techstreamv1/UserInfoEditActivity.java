package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class UserInfoEditActivity extends AppCompatActivity {

    private EditText editUsername, editEmail;
    private EditText dialogUsername, dialogEmail;
    private Button btnEditProfile, btnSignOut, btnSave, btnCancel;
    private Button btnDialogCancel, btnDialogUpdate;
    private LinearLayout editButtons;
    private FrameLayout dialogOverlay;
    private DatabaseReference userRef;
    private boolean isEditMode = false;
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
        initializeViews();

        // Load user data
        loadUserData();

        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        // Main form fields
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);

        // Dialog fields
        dialogUsername = findViewById(R.id.dialogUsername);
        dialogEmail = findViewById(R.id.dialogEmail);

        // Main buttons
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Dialog buttons
        btnDialogCancel = findViewById(R.id.btnDialogCancel);
        btnDialogUpdate = findViewById(R.id.btnDialogUpdate);

        // Layouts
        editButtons = findViewById(R.id.editButtons);
        dialogOverlay = findViewById(R.id.dialogOverlay);

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());
    }

    private void setupClickListeners() {
        // Edit Profile button - Show dialog instead of enabling inline editing
        btnEditProfile.setOnClickListener(v -> showEditDialog());

        // Original inline editing buttons (keep for backward compatibility)
        btnCancel.setOnClickListener(v -> {
            toggleEditMode(false);
            loadUserData();
        });

        btnSave.setOnClickListener(v -> saveUserDataInline());

        // Dialog buttons
        btnDialogCancel.setOnClickListener(v -> hideEditDialog());

        btnDialogUpdate.setOnClickListener(v -> saveUserDataFromDialog());

        // Sign Out button
        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Close dialog when clicking outside
        dialogOverlay.setOnClickListener(v -> hideEditDialog());
    }

    private void showEditDialog() {
        // Pre-populate dialog fields with current values
        dialogUsername.setText(editUsername.getText().toString());
        dialogEmail.setText(editEmail.getText().toString());

        // Show dialog
        dialogOverlay.setVisibility(View.VISIBLE);

        // Dim the background
        dialogOverlay.setAlpha(0f);
        dialogOverlay.animate()
                .alpha(1f)
                .setDuration(200)
                .start();
    }

    private void hideEditDialog() {
        dialogOverlay.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction(() -> dialogOverlay.setVisibility(View.GONE))
                .start();
    }

    private void saveUserDataFromDialog() {
        String newUsername = dialogUsername.getText().toString().trim();
        String newEmail = dialogEmail.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(newUsername)) {
            dialogUsername.setError("Username is required");
            dialogUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(newEmail)) {
            dialogEmail.setError("Email is required");
            dialogEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            dialogEmail.setError("Please enter a valid email");
            dialogEmail.requestFocus();
            return;
        }

        // Show loading state
        btnDialogUpdate.setEnabled(false);
        btnDialogUpdate.setText("Updating...");

        // Save to Firebase
        userRef.child("username").setValue(newUsername);
        userRef.child("email").setValue(newEmail)
                .addOnCompleteListener(task -> {
                    btnDialogUpdate.setEnabled(true);
                    btnDialogUpdate.setText("Update");

                    if (task.isSuccessful()) {
                        // Update main form fields
                        editUsername.setText(newUsername);
                        editEmail.setText(newEmail);

                        // Hide dialog
                        hideEditDialog();

                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void toggleEditMode(boolean enabled) {
        editUsername.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        editButtons.setVisibility(enabled ? View.VISIBLE : View.GONE);
        btnEditProfile.setVisibility(enabled ? View.GONE : View.VISIBLE);
        btnSignOut.setVisibility(enabled ? View.GONE : View.VISIBLE);
        isEditMode = enabled;
    }

    private void loadUserData() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("username").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    // Set main form fields
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

    // Keep original inline editing method for backward compatibility
    private void saveUserDataInline() {
        String newUsername = editUsername.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();

        if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newEmail)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        userRef.child("username").setValue(newUsername);
        userRef.child("email").setValue(newEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        toggleEditMode(false);
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (dialogOverlay.getVisibility() == View.VISIBLE) {
            hideEditDialog();
        } else {
            super.onBackPressed();
        }
    }
}