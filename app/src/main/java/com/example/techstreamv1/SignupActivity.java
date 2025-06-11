package com.example.techstreamv1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Firebase authentication instance
    private EditText etUsername, etEmail, etPassword, etConfirmPassword; // EditText fields for user input
    private Button btnSignup; // Sign Up button
    private TextView loginLink; // Login Link (to navigate to LoginActivity)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Use the updated layout XML

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);
        loginLink = findViewById(R.id.loginLink);

        // Sign Up button click listener
        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            if (password.equals(confirmPassword)) {
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    signUp(email, password); // Call sign-up method
                } else {
                    Toast.makeText(SignupActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignupActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            }
        });

        // Login link click listener
        loginLink.setOnClickListener(v -> {
            // Navigate to Login Activity
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });
    }

    private void signUp(String email, String password) {
        // Create a new user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up successful, show message and proceed
                        Toast.makeText(SignupActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                        // Optionally, navigate to the home or user info page
                        startActivity(new Intent(SignupActivity.this, MainActivity.class)); // Change to desired screen
                        finish(); // Close this activity
                    } else {
                        // Sign-up failed, show error message
                        Toast.makeText(SignupActivity.this, "Sign Up failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
