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
    private Button btnEditProfile, btnSignOut, btnSave, btnCancel;
    private LinearLayout editButtons;
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

        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        editButtons = findViewById(R.id.editButtons);
        ImageView backArrow = findViewById(R.id.backArrow);

        loadUserData();

        btnEditProfile.setOnClickListener(v -> toggleEditMode(true));
        btnCancel.setOnClickListener(v -> {
            toggleEditMode(false);
            loadUserData();
        });
        btnSave.setOnClickListener(v -> saveUserData());
        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
        backArrow.setOnClickListener(v -> finish());
    }

    private void toggleEditMode(boolean enabled) {
        editUsername.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        editButtons.setVisibility(enabled ? View.VISIBLE : View.GONE);
        btnEditProfile.setVisibility(enabled ? View.GONE : View.VISIBLE);
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
                Toast.makeText(UserInfoEditActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
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
        userRef.child("email").setValue(newEmail);
        toggleEditMode(false);
        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
    }
}
