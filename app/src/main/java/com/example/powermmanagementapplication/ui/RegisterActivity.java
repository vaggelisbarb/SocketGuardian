package com.example.powermmanagementapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.powermmanagementapplication.databinding.ActivityRegisterBinding;
import com.example.powermmanagementapplication.repository.UserRepository;
import com.example.powermmanagementapplication.repository.api.FirebaseAuthApi;
import com.example.powermmanagementapplication.repository.firebase.FirebaseAuthImpl;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserRepository userRepository;
    private FirebaseAuthApi fireBaseAuthApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireBaseAuthApi = new FirebaseAuthImpl();
        userRepository = new UserRepository(fireBaseAuthApi);

        setListeners();
    }

    private void setListeners() {

        binding.signIn.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        binding.btnRegister.setOnClickListener(view -> {
            String email = String.valueOf(binding.email.getText());
            String password = String.valueOf(binding.password.getText());
            String confirmPassword = String.valueOf(binding.confirmationPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterActivity.this, "Enter e-mail", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Confirm your password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            registerUser(email, password);

        });
    }

    private void registerUser(String email, String password) {
        binding.progressBar.setVisibility(View.VISIBLE);

        userRepository.registerUser(email, password)
                .addOnCompleteListener(task -> {
                    binding.progressBar.setVisibility(View.GONE);
                    
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        navigateToLoginActivity();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
