package com.example.powermmanagementapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.powermmanagementapplication.R;
import com.example.powermmanagementapplication.databinding.ActivityDeviceDetailsActivityBinding;
import com.example.powermmanagementapplication.domain.DeviceDomain;

public class DeviceDetailActivity extends AppCompatActivity {

    private ActivityDeviceDetailsActivityBinding binding;
    private DeviceDomain deviceObj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();

        // Back button functionality
        binding.backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(intent);
            finish();
        });

        binding.editTitle.setOnClickListener(view -> {
            binding.editTitle.setEnabled(true);
            binding.editTitle.setFocusable(true);
            binding.editTitle.setClickable(true);
            deviceObj.setDeviceName(binding.editTitle.toString());
        });
    }

    private void getBundles() {
        // Initialize device's info
        deviceObj = (DeviceDomain) getIntent().getSerializableExtra("deviceObj");

        int drawableResourced = this.getResources().getIdentifier(deviceObj.setConnectivityIcon(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourced)
                .into(binding.connectivityImg);

        binding.titleTextView.setText(deviceObj.getDeviceName());
        binding.statusTextView.setText(deviceObj.initEnableStatus());
    }
}
