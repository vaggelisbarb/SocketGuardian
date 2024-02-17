package com.example.powermmanagementapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.powermmanagementapplication.R;
import com.example.powermmanagementapplication.databinding.ActivityDeviceDetailsActivityBinding;
import com.example.powermmanagementapplication.databinding.ActivityDevicesBinding;
import com.example.powermmanagementapplication.databinding.LayoutDeviceSettingsDialogBinding;
import com.example.powermmanagementapplication.domain.Detection;
import com.example.powermmanagementapplication.domain.DeviceDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeviceDetailActivity extends AppCompatActivity {

    private ActivityDeviceDetailsActivityBinding binding;
    private DeviceDomain deviceObj;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        getDetectionValuesFromRTDB();

        getBundles();
        setListeners();

    }

    private void setListeners() {
        // Back button functionality
        binding.backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DeviceActivity.class);
            startActivity(intent);
            finish();
        });

        binding.deviceSettingsImg.setOnClickListener(view -> {
            binding.deviceSettingsImg.setEnabled(true);
            binding.deviceSettingsImg.setFocusable(true);
            binding.deviceSettingsImg.setClickable(true);
            deviceObj.setDeviceName(binding.deviceSettingsImg.toString());
        });

        Dialog deviceSettingsDialog = new Dialog(this);
        binding.deviceSettingsImg.setOnClickListener(view -> {
            deviceSettingsDialog.setContentView(R.layout.layout_device_settings_dialog);
            deviceSettingsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            deviceSettingsDialog.setCancelable(true);

            Button confirmButton = deviceSettingsDialog.findViewById(R.id.confirmBtn);
            confirmButton.setOnClickListener(view1 -> {
                EditText doorHeightEditText = deviceSettingsDialog.findViewById(R.id.doorHeightEditText);
                EditText kidsHeightEditText = deviceSettingsDialog.findViewById(R.id.kidsMaxheightEditText);

                String doorHeight = doorHeightEditText.getText().toString();
                String kidsHeight = kidsHeightEditText.getText().toString();

                if (!doorHeight.equals("") && !kidsHeight.equals("")) {
                    DatabaseReference databaseReference = database.getReference();

                    DatabaseReference doorHeightRef = databaseReference.child("device_settings").child("door_height");
                    doorHeightRef.setValue(doorHeight);

                    DatabaseReference childrenHeightRef = databaseReference.child("device_settings").child("children_height");
                    childrenHeightRef.setValue(kidsHeight);

                    deviceSettingsDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Device settings updated!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter correct values", Toast.LENGTH_SHORT).show();
                }
            });

            deviceSettingsDialog.show();

        });

        Dialog socket1DetailsDialog = new Dialog(this);
        binding.socket1View.setOnClickListener(view -> {
            socket1DetailsDialog.setContentView(R.layout.layout_socket_details_dialog);
            socket1DetailsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            socket1DetailsDialog.setCancelable(true);

            RelativeLayout layout = socket1DetailsDialog.findViewById(R.id.colorLayout);
            layout.setBackgroundResource(R.color.primary_color);

            TextView socketIDTextView = socket1DetailsDialog.findViewById(R.id.socketID);
            socketIDTextView.setText(binding.socket1TextView.getText());

            socket1DetailsDialog.show();
        });

        Dialog socket2DetailsDialog = new Dialog(this);
        binding.socket2View.setOnClickListener(view -> {
            socket2DetailsDialog.setContentView(R.layout.layout_socket_details_dialog);
            socket2DetailsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            socket2DetailsDialog.setCancelable(true);

            RelativeLayout layout = socket2DetailsDialog.findViewById(R.id.colorLayout);
            layout.setBackgroundResource(R.color.alternative_color);

            TextView socketIDTextView = socket2DetailsDialog.findViewById(R.id.socketID);
            socketIDTextView.setText(binding.socket2TextView.getText());

            socket2DetailsDialog.show();
        });

        Dialog socket3DetailsDialog = new Dialog(this);
        binding.socket3View.setOnClickListener(view -> {
            socket3DetailsDialog.setContentView(R.layout.layout_socket_details_dialog);
            socket3DetailsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            socket3DetailsDialog.setCancelable(true);

            RelativeLayout layout = socket3DetailsDialog.findViewById(R.id.colorLayout);
            layout.setBackgroundResource(R.color.purple);

            TextView socketIDTextView = socket3DetailsDialog.findViewById(R.id.socketID);
            socketIDTextView.setText(binding.socket3TextView.getText());

            socket3DetailsDialog.show();
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

    private void getDetectionValuesFromRTDB() {

        DatabaseReference detectionsReference = database.getReference("detection");
        detectionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Detection detection = dataSnapshot.getValue(Detection.class);
                if (detection != null) {
                    binding.adultsDetectedTextView.setText(detection.getAdultsDetected());
                    binding.childrenDetectedTextView.setText(detection.getChildrenDetected());
                    Log.i("RTDB", detection.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                // Log an error message or handle it as needed
            }
        });
    }
}
