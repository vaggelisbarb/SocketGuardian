package com.example.powermmanagementapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.powermmanagementapplication.databinding.ActivityLoginBinding;
import com.example.powermmanagementapplication.model.authentication.User;
import com.example.powermmanagementapplication.repository.UserRepository;
import com.example.powermmanagementapplication.repository.api.FirebaseAuthApi;
import com.example.powermmanagementapplication.repository.firebase.FirebaseAuthImpl;
import com.example.powermmanagementapplication.utils.FirebaseAuthUtils;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserRepository userRepository;
    private FirebaseAuthApi fireBaseAuthApi;
    private boolean isRegistering = false;

    @Override
    public void onStart() {
        super.onStart();

        if (FirebaseAuthUtils.isUserAuthenticated(fireBaseAuthApi) && !isRegistering) {
            User currentUser = userRepository.getCurrentUser();
            if (currentUser != null)
                navigateToDeviceActivity();
            else
                Toast.makeText(LoginActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireBaseAuthApi = new FirebaseAuthImpl();
        userRepository = new UserRepository(fireBaseAuthApi);

        setListeners();

    }

    private void navigateToDeviceActivity() {
        Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
        startActivity(intent);
        finish();
    }

    private void setListeners() {
        binding.btnLogin.setOnClickListener(view -> {
            String email = String.valueOf(binding.email.getText());
            String password = String.valueOf(binding.password.getText());

            if(TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter e-mail", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(email, password);
        });

        binding.btnRegister.setOnClickListener(view -> {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                // Set isRegistering flag to true when registration activity starts
                isRegistering = true;
                // Finish LoginActivity to remove it from the back stack
                //finish();
        });
    }

    private void loginUser(String email, String password) {
        binding.progressBar.setVisibility(View.VISIBLE);

        userRepository.loginUser(email, password)
                .addOnCompleteListener(task -> {
                    binding.progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Sign-in Successful", Toast.LENGTH_SHORT).show();
                        navigateToDeviceActivity();
                    }else {
                        Toast.makeText(LoginActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
                    }
        });
    }

}
