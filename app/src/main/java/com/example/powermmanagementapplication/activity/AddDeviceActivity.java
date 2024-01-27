package com.example.powermmanagementapplication.activity;

import static java.lang.System.out;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.powermmanagementapplication.databinding.ActivityNewDeviceBinding;
import com.example.powermmanagementapplication.domain.BtConnection;


public class AddDeviceActivity extends AppCompatActivity {

    private ActivityNewDeviceBinding binding;

    private ActivityResultLauncher<Intent> activityResultLauncher = null;
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    private BtConnection btConnection;

    private BluetoothAdapter bluetoothAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();

    }

    private void setListeners() {

        binding.back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(intent);
            finish();
        });


        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Log.e("Activity result", "OK");
                        // There are no request codes
                        Intent data = result.getData();
                        data.getStringExtra("details");
                    }
                });

        // Turn on Bluetooth component
        binding.btSwitch.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(AddDeviceActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= 31) {
                    ActivityCompat.requestPermissions(AddDeviceActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 100);
                    return;
                }
            }

            BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

            if (Build.VERSION.SDK_INT >= 31)
                bluetoothAdapter = bluetoothManager.getAdapter();
            else
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter.isEnabled())
                bluetoothAdapter.disable();
            else
                bluetoothAdapter.isEnabled();
        });
    }
}
