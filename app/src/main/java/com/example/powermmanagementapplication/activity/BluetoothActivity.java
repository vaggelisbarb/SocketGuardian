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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.powermmanagementapplication.databinding.ActivityBtDeviceBinding;

import java.util.ArrayList;
import java.util.List;

public class BluetoothActivity extends AppCompatActivity {

    private ActivityBtDeviceBinding binding;
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 2;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 3;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> discoveredDevicesArrayAdapter;
    private List<String> discoveredDevicesList;
    private boolean isReceiverRegistered = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBtDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBtLauncher();
        setListeners();
    }

    private void setListeners() {

        binding.backButton.setOnClickListener(view -> {
            Intent deviceIntent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(deviceIntent);
            finish();
        });

        binding.enableBtButton.setOnClickListener(view -> {
            if (bluetoothAdapter == null) {
                // Device does not support Bluetooth
                Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!bluetoothAdapter.isEnabled()) {
                // If device supports Bluetooth, request permissions and enable it
                requestBluetoothPermission();
            } else {
                Toast.makeText(this, "Bluetooth is already enabled", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the List for discovered devices
        discoveredDevicesList = new ArrayList<>();
        discoveredDevicesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, discoveredDevicesList);
        binding.btDevicesListView.setAdapter(discoveredDevicesArrayAdapter);

        binding.searchDevicesButton.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission not granted, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_FINE_LOCATION_PERMISSION);
            }
        });
    }

    private void initBtLauncher() {
        // Initialize the ActivityResultLauncher
        enableBluetoothLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Bluetooth enabled successfully
                        Toast.makeText(this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
                    } else {
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
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH},
                    REQUEST_BLUETOOTH_PERMISSION);
        } else {
            // Bluetooth permission already granted, try to enable Bluetooth
            enableBluetooth();
        }
    }

    private void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        enableBluetoothLauncher.launch(enableBtIntent);
    }


    private void performBluetoothDiscovery() {
        // Register for Bluetooth discovery broadcasts
        if (!isReceiverRegistered) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            filter.addAction(BluetoothDevice.ACTION_FOUND);

            try {
                registerReceiver(receiver, filter);
                isReceiverRegistered = true;
                Log.i("Receiver Registration", "Receiver registered successfully");
            }catch (Exception e) {
                isReceiverRegistered = false;
                Log.e("Receiver Registration", "Failed to register BroadcastReceiver: " + e.getMessage());
            }
        }

        // Start Bluetooth discovery
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION_PERMISSION);
        } else {
            // Permission granted, start Bluetooth discovery
            bluetoothAdapter.startDiscovery();
        }
    }

    // Broadcast receiver for Bluetooth discovery
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                binding.progressBar.setVisibility(View.VISIBLE);
                // Discovery process has started
                Log.i("Broadcast Receiver", "Discovery started");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                binding.progressBar.setVisibility(View.GONE);
                // Discovery process has finished
                Log.i("Broadcast Receiver", "Discovery finished");
            }else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // A new bluetooth device has been discovered
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device!=null ){
                    @SuppressLint("MissingPermission")
                    String deviceName = device.getName();
                    String deviceAddress = device.getAddress();
                    String discoveredDeviceInfo = deviceName + " - " + deviceAddress;

                    Log.i("Broadcast Receiver", discoveredDeviceInfo);

                    discoveredDevicesList.add(discoveredDeviceInfo);
                    discoveredDevicesArrayAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver only if it has been registered previously
        if (isReceiverRegistered) {
            unregisterReceiver(receiver);
            isReceiverRegistered = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_FINE_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("Permission", "Location permission granted");
                // Location permission granted, start Bluetooth discovery
                performBluetoothDiscovery();
            } else {
                Log.i("Permission", "Location permission denied");
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
