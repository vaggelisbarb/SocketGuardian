package com.example.powermmanagementapplication.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.powermmanagementapplication.R;
import com.example.powermmanagementapplication.databinding.ActivityDeviceDetailsActivityBinding;
import com.example.powermmanagementapplication.model.device.Device;
import com.example.powermmanagementapplication.model.device.PowerSocket;
import com.example.powermmanagementapplication.repository.DeviceRepository;
import com.example.powermmanagementapplication.repository.PowerSocketRepository;
import com.example.powermmanagementapplication.repository.callback.FirebaseDeviceCallBack;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdateDeviceCallback;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdatePowerSocketCallback;
import com.example.powermmanagementapplication.utils.DeviceUiUtils;
import com.example.powermmanagementapplication.utils.PowerSocketUiUtils;


public class DeviceDetailActivity extends AppCompatActivity {

    private static final String UPDATE_POWER_SOCKETS_TAG = "FirebaseUpdatePowerSocketCallback";
    private static final String DEVICES_TAG = "FirebaseAllDevicesCallback";
    private static final String DEVICE_DATA_TAG = "FirebaseDeviceCallback";
    private static final String UPDATE_DEVICE_TAG = "FirebaseUpdateDeviceCallback";

    private ActivityDeviceDetailsActivityBinding binding;
    private Device deviceObj;
    private DeviceRepository deviceRepository;
    private PowerSocketRepository powerSocketRepository;
    private PowerSocket powerSocket1;
    private PowerSocket powerSocket2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deviceRepository = new DeviceRepository();
        powerSocketRepository = new PowerSocketRepository();

        deviceObj = (Device) getIntent().getSerializableExtra("deviceId");
        retrieveDeviceData(deviceObj.getDeviceId());

        getBundles();
        setListeners();
        initPowerSockets();
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

            Button confirmButton = deviceSettingsDialog.findViewById(R.id.confirmButton);
            confirmButton.setOnClickListener(view1 -> {
                EditText doorHeightEditText = deviceSettingsDialog.findViewById(R.id.doorHeightEditText);
                EditText kidsHeightEditText = deviceSettingsDialog.findViewById(R.id.kidsMaxHeightEditText);

                String doorHeight = doorHeightEditText.getText().toString();
                String kidsHeight = kidsHeightEditText.getText().toString();

                if (!doorHeight.equals("") && !kidsHeight.equals("")) {

                    deviceRepository.updateDeviceSettings(deviceObj.getDeviceId(),
                            Integer.parseInt(doorHeight),
                            Integer.parseInt(kidsHeight),
                            new FirebaseUpdateDeviceCallback() {
                                @Override
                                public void onDeviceStatusUpdateSuccess() {
                                    deviceSettingsDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Device settings updated!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onDeviceStatusUpdateFailure(String errorMessage) {
                                    Toast.makeText(getApplicationContext(), "Please enter correct values", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            deviceSettingsDialog.show();

        });

        binding.powerImage1.setOnClickListener(view -> {
            PowerSocket powerSocket1 = deviceObj.getPowerSockets().get("power_socket_id_1");
            if (powerSocket1 != null) {
                powerSocketRepository.updatePowerSocketStatus(deviceObj.getDeviceId(),
                        powerSocket1.getSocketId(),
                        powerSocket1.toogleStatus(),
                        new FirebaseUpdatePowerSocketCallback() {
                            @Override
                            public void onPowerSocketStatusUpdateSuccess() {
                                setPowerSocketStatusIcon(binding.powerImage1, powerSocket1);
                                Log.i(UPDATE_POWER_SOCKETS_TAG, "Power socket status updated successfully: " + powerSocket1.toString());
                                Toast.makeText(DeviceDetailActivity.this, powerSocket1.getSocketName()+" socket status is "+powerSocket1.getStatus(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPowerSocketStatusUpdateFailure(String errorMessage) {
                                Log.e(UPDATE_POWER_SOCKETS_TAG, "Failed to update power socket status: " + errorMessage);
                            }
                        });
            }
        });

        binding.powerImage2.setOnClickListener(view -> {
            PowerSocket powerSocket2 = deviceObj.getPowerSockets().get("power_socket_id_2");
            if (powerSocket2 != null) {
                powerSocketRepository.updatePowerSocketStatus(deviceObj.getDeviceId(),
                        powerSocket2.getSocketId(),
                        powerSocket2.toogleStatus(),
                        new FirebaseUpdatePowerSocketCallback() {
                            @Override
                            public void onPowerSocketStatusUpdateSuccess() {
                                setPowerSocketStatusIcon(binding.powerImage2, powerSocket2);
                                Log.i(UPDATE_POWER_SOCKETS_TAG, "Power socket status updated successfully: " + powerSocket2.toString());
                                Toast.makeText(DeviceDetailActivity.this, powerSocket2.getSocketName()+" socket status is "+powerSocket2.getStatus(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPowerSocketStatusUpdateFailure(String errorMessage) {
                                Log.e(UPDATE_POWER_SOCKETS_TAG, "Failed to update power socket status: " + errorMessage);
                            }
                        });
            }
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

            TextView socketTypeTextView = socket1DetailsDialog.findViewById(R.id.socketTypeTextView);
            socketTypeTextView.setText(powerSocket1.getSocketType().toUpperCase());

            setPowerSocketStatusIcon(socket1DetailsDialog.findViewById(R.id.electricityStatus), powerSocket1);

            socket1DetailsDialog.show();
        });

        Dialog socket2DetailsDialog = new Dialog(this);
        binding.socket2View.setOnClickListener(view -> {
            socket2DetailsDialog.setContentView(R.layout.layout_socket_details_dialog);
            socket2DetailsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            socket2DetailsDialog.setCancelable(true);

            RelativeLayout layout = socket2DetailsDialog.findViewById(R.id.colorLayout);
            layout.setBackgroundResource(R.color.alternative_color);

            TextView socketTypeTextView = socket2DetailsDialog.findViewById(R.id.socketTypeTextView);
            socketTypeTextView.setText(powerSocket2.getSocketType().toUpperCase());


            TextView socketIDTextView = socket2DetailsDialog.findViewById(R.id.socketID);
            socketIDTextView.setText(binding.socket2TextView.getText());

            setPowerSocketStatusIcon(socket2DetailsDialog.findViewById(R.id.electricityStatus), powerSocket2);

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
        int drawableResourced = this.getResources().getIdentifier(DeviceUiUtils.generateConnectivityIcon(deviceObj.getConnectivity()), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourced)
                .into(binding.connectivityImg);

        binding.titleTextView.setText(deviceObj.getDeviceName());
        binding.statusTextView.setText(deviceObj.getDeviceStatus().toUpperCase());

        if (deviceObj.getDeviceStatus().equalsIgnoreCase("enabled"))
            binding.statusTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        else if (deviceObj.getDeviceStatus().equalsIgnoreCase("disabled"))
            binding.statusTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));

    }

    private void retrieveDeviceData(String deviceId) {
        deviceRepository.getDeviceData(deviceId, new FirebaseDeviceCallBack() {
            @Override
            public void onDeviceDataRetrieved(Device device) {
                binding.adultsDetectedTextView.setText(device.getDetection().getAdultsDetected());
                binding.childrenDetectedTextView.setText(device.getDetection().getChildrenDetected());
                Log.i(DEVICE_DATA_TAG, device.toString());
            }

            @Override
            public void onDeviceDataRetrievalFailure(String errorMessage) {
                Log.e(DEVICE_DATA_TAG, errorMessage);
            }
        });
    }

    private void initPowerSockets() {
        powerSocket1 = deviceObj.getPowerSockets().get("power_socket_id_1");
        binding.socket1TextView.setText(powerSocket1.getSocketName());
        setPowerSocketStatusIcon(binding.powerImage1, powerSocket1);

        powerSocket2 = deviceObj.getPowerSockets().get("power_socket_id_2");
        binding.socket2TextView.setText(powerSocket2.getSocketName());
        setPowerSocketStatusIcon(binding.powerImage2, powerSocket2);

    }

    private void setPowerSocketStatusIcon(ImageView statusIconView, PowerSocket powerSocket) {
        int drawableResourced = this.getResources().getIdentifier(PowerSocketUiUtils.generateStatusIcon(powerSocket.getStatus()), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourced)
                .into(statusIconView);
    }

}
