package com.example.powermmanagementapplication.repository.api;

import com.example.powermmanagementapplication.repository.callback.FirebaseAllDevicesCallback;
import com.example.powermmanagementapplication.repository.callback.FirebaseDeviceCallBack;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdateDeviceCallback;

/**
 * Interface defining methods for interacting with the Firebase Realtime Database to perform device-related operations.
 */
public interface FirebaseDeviceApi {

    /**
     * Retrieves device data for the specified device ID from the database.
     *
     * @param deviceId The ID of the device to retrieve data for.
     * @param callback The callback to handle the result of the operation.
     */
    void getDeviceData(String deviceId, FirebaseDeviceCallBack callback);

    // Method to retrieve all devices from the database
    void getAllDevices(FirebaseAllDevicesCallback callback);

    /**
     * Deletes a device with the specified device ID from the database.
     *
     * @param deviceId The ID of the device to delete.
     * @param callback The callback to handle the result of the operation.
     */
    void deleteDevice(String deviceId, FirebaseAllDevicesCallback callback);

    /**
     * Updates the status of a device with the specified device ID in the database.
     *
     * @param deviceId  The ID of the device to update.
     * @param newStatus The new status to set for the device.
     * @param callback  The callback to handle the result of the operation.
     */
    void updateDeviceStatus(String deviceId, String newStatus, FirebaseUpdateDeviceCallback callback);

    void updateDeviceSettings(String deviceId, int doorHeight, int childrenHeight, FirebaseUpdateDeviceCallback callback);
}
