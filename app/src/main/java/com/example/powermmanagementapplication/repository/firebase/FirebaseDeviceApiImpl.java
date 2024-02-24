package com.example.powermmanagementapplication.repository.firebase;

import androidx.annotation.NonNull;

import com.example.powermmanagementapplication.model.device.Device;
import com.example.powermmanagementapplication.repository.api.FirebaseDeviceApi;
import com.example.powermmanagementapplication.repository.callback.FirebaseAllDevicesCallback;
import com.example.powermmanagementapplication.repository.callback.FirebaseDeviceCallBack;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdateDeviceCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the FirebaseDeviceApi interface for interacting with Firebase Realtime Database
 * to perform device-related operations.
 */
public class FirebaseDeviceApiImpl implements FirebaseDeviceApi {

    private DatabaseReference deviceRef;

    public FirebaseDeviceApiImpl() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        deviceRef = firebaseDatabase.getReference("devices");
    }

    @Override
    public void getDeviceData(String deviceId, FirebaseDeviceCallBack callback) {
        // Retrieve device data from Firebase Realtime Database
        deviceRef.child(deviceId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Device device = snapshot.getValue(Device.class);
                if (device != null)
                    callback.onDeviceDataRetrieved(device);
                else
                    callback.onDeviceDataRetrievalFailure("Device data not found");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDeviceDataRetrievalFailure(error.getMessage());
            }
        });
    }

    @Override
    public void getAllDevices(FirebaseAllDevicesCallback callback) {
        deviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Device> devices = new ArrayList<>();
                for (DataSnapshot deviceSnapshot : snapshot.getChildren()) {
                    Device device = deviceSnapshot.getValue(Device.class);
                    if (device != null)
                        devices.add(device);
                }
                callback.onDevicesDataRetrieved(devices);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDevicesDataRetrievalFailure(error.getMessage());
            }
        });
    }

    @Override
    public void deleteDevice(String deviceId, FirebaseAllDevicesCallback callback) {
        // Delete device data from Firebase Realtime Database
        deviceRef.child(deviceId).removeValue((databaseError, databaseReference) -> {
            if (databaseError == null) {
                // Device deletion successful
                //callback.onDataLoaded();
            } else {
                // Error occurred while deleting device
                callback.onDevicesDataRetrievalFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void updateDeviceStatus(String deviceId, String newStatus, FirebaseUpdateDeviceCallback callback) {
        // Get a reference to the status field under the device's ID
        DatabaseReference deviceStatusRef = deviceRef.child(deviceId).child("deviceStatus");

        // Update the status in the Firebase Realtime Database
        deviceStatusRef.setValue(newStatus)
                .addOnSuccessListener(aVoid -> {
                    // Status update successful
                    callback.onDeviceStatusUpdateSuccess();
                })
                .addOnFailureListener(e -> {
                    // Status update failed
                    callback.onDeviceStatusUpdateFailure(e.getMessage());
                });

    }

    @Override
    public void updateDeviceSettings(String deviceId, int doorHeight, int childrenHeight, FirebaseUpdateDeviceCallback callback) {
        DatabaseReference settingDoorHeightRef = deviceRef.child(deviceId).child("settings").child("doorHeight");
        settingDoorHeightRef.setValue(doorHeight)
                .addOnSuccessListener(aVoid -> {
                    callback.onDeviceStatusUpdateSuccess();
                })
                .addOnFailureListener(e -> {
                   callback.onDeviceStatusUpdateFailure(e.getMessage());
                });

        DatabaseReference settingChildrenHeightRef = deviceRef.child(deviceId).child("settings").child("childrenHeight");
        settingChildrenHeightRef.setValue(childrenHeight)
                .addOnSuccessListener(aVoid -> {
                    callback.onDeviceStatusUpdateSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onDeviceStatusUpdateFailure(e.getMessage());
                });

    }
}
