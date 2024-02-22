package com.example.powermmanagementapplication.repository.api;

import com.example.powermmanagementapplication.repository.callback.FirebaseUpdatePowerSocketCallback;

public interface FirebasePowerSocketApi {

    /**
     * Updates the status of a power socket node on Firebase.
     *
     * @param deviceId   The ID of the device containing the power socket.
     * @param socketId   The ID of the power socket to update.
     * @param newStatus  The new status value to set for the power socket.
     * @param callback   The callback to handle the result of the update operation.
     */
    void updatePowerSocketStatus(String deviceId, String socketId, String newStatus, FirebaseUpdatePowerSocketCallback callback);

}
