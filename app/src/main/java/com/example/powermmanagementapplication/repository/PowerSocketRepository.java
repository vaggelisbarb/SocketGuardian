package com.example.powermmanagementapplication.repository;

import com.example.powermmanagementapplication.repository.api.FirebasePowerSocketApi;
import com.example.powermmanagementapplication.repository.callback.FirebasePowerSocketCallback;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdatePowerSocketCallback;
import com.example.powermmanagementapplication.repository.firebase.FirebasePowerSocketApiImpl;

public class PowerSocketRepository {

    private FirebasePowerSocketApi firebasePowerSocketApi;

    public PowerSocketRepository() {
        firebasePowerSocketApi = new FirebasePowerSocketApiImpl();
    }

    public void updatePowerSocketStatus(String deviceId, String socketId, String newStatus, FirebaseUpdatePowerSocketCallback callback) {
        firebasePowerSocketApi.updatePowerSocketStatus(deviceId, socketId, newStatus, callback);
    }

    public void getPowerSocket(String deviceId, String powerSocketId, FirebasePowerSocketCallback callback) {
        firebasePowerSocketApi.getPowerSocket(deviceId, powerSocketId, callback);
    }

}
