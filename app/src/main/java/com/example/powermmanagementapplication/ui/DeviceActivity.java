package com.example.powermmanagementapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.powermmanagementapplication.adapter.DeviceAdapter;
import com.example.powermmanagementapplication.databinding.ActivityDevicesBinding;
import com.example.powermmanagementapplication.model.device.Device;
import com.example.powermmanagementapplication.repository.DeviceRepository;
import com.example.powermmanagementapplication.repository.callback.FirebaseAllDevicesCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class DeviceActivity extends AppCompatActivity {

    private DeviceRepository deviceRepository;
    private ActivityDevicesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deviceRepository = new DeviceRepository();

        fetchAndDisplayDevices();
        setListeners();
    }

    private void setListeners() {
        binding.addDeviceBtn.setOnClickListener(view -> {
            Intent addDeviceIntent = new Intent(getApplicationContext(), BluetoothActivity.class);
            startActivity(addDeviceIntent);
            finish();
            //this.showNewDevicePopUpMenu(view);
        });

        binding.layoutLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(DeviceActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchAndDisplayDevices(){
        deviceRepository.getAllDevices(new FirebaseAllDevicesCallback() {
            @Override
            public void onDevicesDataRetrieved(List<Device> data) {
                binding.activeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                binding.activeRecyclerView.setAdapter(new DeviceAdapter((ArrayList<Device>) data));

                for (Device device : data)
                    Log.i("FirebaseAllDevicesCallback", device.toString());

            }

            @Override
            public void onDevicesDataRetrievalFailure(String errorMessage) {
                Log.e("FirebaseAllDevicesCallback", errorMessage);
            }
        });
    }

    /*
    private void showNewDevicePopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.add_device_menu, popupMenu.getMenu());

        popupMenu.show();
    }
     */
}
