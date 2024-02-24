package com.example.powermmanagementapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.powermmanagementapplication.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding activitySettingsBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(activitySettingsBinding.getRoot());

        setListeners();
    }

    private void setListeners() {

        activitySettingsBinding.backButton.setOnClickListener(view -> {
            Intent devicesIntent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(devicesIntent);
            finish();
        });
    }
}
