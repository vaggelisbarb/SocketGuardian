package com.example.powermmanagementapplication.activity;

import static java.lang.System.out;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.powermmanagementapplication.databinding.ActivityNewDeviceBinding;


public class AddDeviceActivity extends AppCompatActivity {

    private ActivityNewDeviceBinding binding;

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(intent);
            finish();
        });

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
            out.append("Device not supported");

        binding.btSwitch.setOnClickListener(view -> {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            }
        });
    }
}
