package com.example.powermmanagementapplication.repository.firebase;

import androidx.annotation.NonNull;

import com.example.powermmanagementapplication.repository.api.FirebasePowerSocketApi;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdatePowerSocketCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebasePowerSocketApiImpl implements FirebasePowerSocketApi {

    private DatabaseReference deviceRef;

    public FirebasePowerSocketApiImpl() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        deviceRef = firebaseDatabase.getReference("devices");
    }

    @Override
    public void updatePowerSocketStatus(String deviceId, String socketId, String newStatus, FirebaseUpdatePowerSocketCallback callback) {
        DatabaseReference powerSocketStatusRef = deviceRef.child(deviceId)
                .child("powerSockets")
                .child(socketId)
                .child("status");

        powerSocketStatusRef.setValue(newStatus)
                .addOnSuccessListener(unused -> callback.onPowerSocketStatusUpdateSuccess())
                .addOnFailureListener(e -> callback.onPowerSocketStatusUpdateFailure(e.getMessage()));
    }
}
