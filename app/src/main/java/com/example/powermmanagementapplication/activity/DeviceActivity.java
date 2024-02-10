package com.example.powermmanagementapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.powermmanagementapplication.R;
import com.example.powermmanagementapplication.adapter.DeviceAdapter;
import com.example.powermmanagementapplication.databinding.ActivityDevicesBinding;
import com.example.powermmanagementapplication.domain.DeviceDomain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class DeviceActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private ActivityDevicesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initDevicesRecyclerView();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

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

    private void initDevicesRecyclerView(){
        ArrayList<DeviceDomain> devices = new ArrayList<>();
        devices.add(new DeviceDomain("1", "Kid's room 1", "Wi-Fi", "19-1-2024 11:33", true, true));
        devices.add(new DeviceDomain("2", "Kid's room 2", "Bluetooth", "19-1-2024 12:20", true, true));
        devices.add(new DeviceDomain("2", "Kid's room 3", "Bluetooth", "19-1-2024 12:20", true, false));


        binding.activeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.activeRecyclerView.setAdapter(new DeviceAdapter(devices));
    }

    private void showNewDevicePopUpMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.add_device_menu, popupMenu.getMenu());

        popupMenu.show();
    }
}
