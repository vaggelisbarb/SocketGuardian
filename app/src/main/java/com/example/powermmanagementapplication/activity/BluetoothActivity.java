package com.example.powermmanagementapplication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.powermmanagementapplication.databinding.ActivityBtDeviceBinding;

public class BluetoothActivity extends AppCompatActivity {

    private ActivityBtDeviceBinding binding;
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 2;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> discoveredDevicesArrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBtDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBtLauncher();
        setListeners();

    }

    private void setListeners() {

        // Back button
        binding.back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(intent);
            finish();
        });

        // Bluetooth enable button
        binding.btSwitch.setOnClickListener(view -> {
            if (bluetoothAdapter == null) {
                // Device does not support Bluetooth
                Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                // If device supports bluetooth, request permissions
                requestBluetoothPermission();
            }else {
                Toast.makeText(this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show();
            }
        });


        discoveredDevicesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        binding.btDevicesListView.setAdapter(discoveredDevicesArrayAdapter);

        binding.findBtBtn.setOnClickListener(view -> {

            // Register for Bluetooth discovery broadcasts
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);

            // Start Bluetooth discovery
            bluetoothAdapter.startDiscovery();
        });

    }

    // Broadcast receiver for Bluetooth discovery
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                @SuppressLint("MissingPermission") String deviceName = device.getName();
                String deviceAddress = device.getAddress();
                String discoveredDeviceInfo = deviceName + " - " + deviceAddress;

                discoveredDevicesArrayAdapter.add(discoveredDeviceInfo);
                discoveredDevicesArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    private void initBtLauncher() {
        // Initialize the ActivityResultLauncher
        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if (result.getResultCode() == Activity.RESULT_OK){
                        // Bluetooth enabled successfully
                        Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
                    }else{
                        // Bluetooth enabling cancelled or failed
                        Toast.makeText(this, "Bluetooth enabling cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void requestBluetoothPermission() {
        // Check if the app has Bluetooth permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {
            // Request Bluetooth permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                        REQUEST_BLUETOOTH_PERMISSION);
            }
        } else {
            // Bluetooth permission already granted, try to enable Bluetooth
            enableBluetooth();
        }
    }

    private void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        enableBluetoothLauncher.launch(enableBtIntent);
    }

}
