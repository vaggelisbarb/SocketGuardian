package com.example.powermmanagementapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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

import com.example.powermmanagementapplication.databinding.ActivityBtDeviceBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {

    private ActivityBtDeviceBinding binding;
    private ActivityResultLauncher<Intent> enableBluetoothLauncher;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 2;
    private static final int REQUEST_FINE_LOCATION_PERMISSION = 3;
    private static final int REQUEST_LOCATION_AND_BLUETOOTH_PERMISSION = 4;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> discoveredDevicesArrayAdapter;
    private List<String> discoveredDevicesList;
    private boolean isReceiverRegistered = false;
    private BluetoothSocket bluetoothSocket;
    private BroadcastReceiver receiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBtDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBtLauncher();
        setListeners();

        /**
         * Broadcast receiver for Bluetooth discovery
         */
        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                Log.e("Check", "HERE");

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

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isReceiverRegistered) {
            filter = new IntentFilter();
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            filter.addAction(BluetoothDevice.ACTION_FOUND);

            try {
                registerReceiver(receiver, filter);
                isReceiverRegistered = true;
                Log.i("Receiver Registration", "Receiver registered successfully");
            } catch (Exception e) {
                isReceiverRegistered = false;
                Log.e("Receiver Registration", "Failed to register BroadcastReceiver: " + e.getMessage());
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the BroadcastReceiver when the activity is paused
        if (isReceiverRegistered) {
            unregisterReceiver(receiver);
            isReceiverRegistered = false;
            Log.e("Pause", "Receiver unregistered");
        }
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
            requestLocationAndBtScanPermission();
        });

        binding.btDevicesListView.setOnItemClickListener(((adapterView, view, position, id) -> {
            String selectedItem = discoveredDevicesArrayAdapter.getItem(position);

            String[] parts = selectedItem.split(" - ");
            String deviceName = parts[0];
            String deviceAddress = parts[1];

            BluetoothDevice selectedBtDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);
            if (selectedBtDevice != null) {
                connectToDevice(selectedBtDevice);
            }

        }));
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

    /**
     * Requesting bluetooth permissions from the user.
     * If it's granted enabling bluetooth process is executed
     */
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

    /**
     * Enables device's bluetooth
     */
    private void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        enableBluetoothLauncher.launch(enableBtIntent);
    }

    /**
     * Requesting fine location permission from user
     */
    private void requestLocationAndBtScanPermission() {
        // Check if the app has fine location and Bluetooth permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                        != PackageManager.PERMISSION_GRANTED) {
            // Request fine location and Bluetooth permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN},
                    REQUEST_LOCATION_AND_BLUETOOTH_PERMISSION);
        } else {
            // Fine location and Bluetooth permissions already granted, perform Bluetooth discovery
            performBluetoothDiscovery();
        }

    }

    /**
     * Requesting location permission from the user.
     * If it's granted bluetooth discovery process is executed
     */
    private void performBluetoothDiscovery() {
        // Register for Bluetooth discovery broadcasts
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, start Bluetooth discovery
            Log.i("Bluetooth Adapter", String.valueOf(bluetoothAdapter.isEnabled()));
            bluetoothAdapter.startDiscovery();
        } else {
            // Permission not granted, handle accordingly (e.g., show a message to the user)
            Log.e("Permission", "Fine location permission not granted");
        }
    }

    /**
     * @param requestCode  The request code passed in
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_AND_BLUETOOTH_PERMISSION) {
            boolean locationPermissionGranted = grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean bluetoothPermissionGranted = grantResults.length > 1 &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if (locationPermissionGranted && bluetoothPermissionGranted) {
                Log.i("Permission", "Location and Bluetooth Scan permissions granted");
                // Location and Bluetooth permissions granted, start Bluetooth discovery
                performBluetoothDiscovery();
            } else {
                Log.i("Permission", "Location and/or Bluetooth Scan permissions denied");
                Toast.makeText(this, "Location and/or Bluetooth Scan permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    /**
     * @param bluetoothDevice The selected device to establish a bluetooth connection with
     */
    @SuppressLint("MissingPermission")
    private void connectToDevice(BluetoothDevice bluetoothDevice) {
        // Create a BluetoothSocket for the device
        try {
            // TODO : Change UUID to our ESP32's UUID
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
            Log.e("Connection", e.getMessage());
            return;
        }

        new Thread(() -> {
            try {
                bluetoothSocket.connect();
                Log.i("Connection", "Connected to device : " + bluetoothDevice.getName());
                // TODO : Start communicating with the ESP32 / Communication protocol goes here ...
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Connection", e.getMessage());
                try {
                    bluetoothSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Log.d("Connection", "Closing bluetooth socket");
                }
            }
        }).start();

    }
}
