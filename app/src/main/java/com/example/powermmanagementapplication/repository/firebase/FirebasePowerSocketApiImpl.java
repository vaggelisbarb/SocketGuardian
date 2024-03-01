package com.example.powermmanagementapplication.repository.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.powermmanagementapplication.model.device.PowerSocket;
import com.example.powermmanagementapplication.repository.api.FirebasePowerSocketApi;
import com.example.powermmanagementapplication.repository.callback.FirebasePowerSocketCallback;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdatePowerSocketCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @Override
    public void getPowerSocket(String deviceId, String powerSocketId, FirebasePowerSocketCallback callback) {
        DatabaseReference powerSocketRef = deviceRef.child(deviceId)
                .child("powerSockets")
                .child(powerSocketId);
        Log.d("GetPowerSocket", powerSocketRef.toString());

        powerSocketRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PowerSocket powerSocket = snapshot.getValue(PowerSocket.class);
                if (powerSocket != null)
                    callback.onPowerSocketDataRetrieved(powerSocket);
                else
                    callback.onPowerSocketDataRetrievalFailure("Cannot retrieve power socket's data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onPowerSocketDataRetrievalFailure(error.getMessage());
            }
        });
    }
}
