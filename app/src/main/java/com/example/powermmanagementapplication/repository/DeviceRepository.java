package com.example.powermmanagementapplication.repository;

import com.example.powermmanagementapplication.repository.api.FirebaseDeviceApi;
import com.example.powermmanagementapplication.repository.callback.FirebaseAllDevicesCallback;
import com.example.powermmanagementapplication.repository.callback.FirebaseDeviceCallBack;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdateDeviceCallback;
import com.example.powermmanagementapplication.repository.firebase.FirebaseDeviceApiImpl;

/**
 * Repository class responsible for interacting with device data.
 */
public class DeviceRepository {

    private FirebaseDeviceApi firebaseDeviceApi;


    public DeviceRepository() {
        firebaseDeviceApi = new FirebaseDeviceApiImpl();
    }

    /**
     * Constructor for DeviceRepository.
     *
     * @param firebaseDeviceApi An instance of FirebaseDeviceApi for accessing device data from Firebase.
     */
    public DeviceRepository(FirebaseDeviceApi firebaseDeviceApi) {
        this.firebaseDeviceApi = firebaseDeviceApi;
    }

    /**
     * Retrieves all devices from Firebase.
     *
     * @param callback The callback to handle the retrieved devices.
     */
    public void getAllDevices(FirebaseAllDevicesCallback callback) {
        firebaseDeviceApi.getAllDevices(callback);
    }

    /**
     * Retrieves device data for the specified device ID.
     *
     * @param deviceId The ID of the device to retrieve data for.
     * @param callback The callback to handle the result of the operation.
     */
    public void getDeviceData(String deviceId, FirebaseDeviceCallBack callback) {
        firebaseDeviceApi.getDeviceData(deviceId, callback);
    }

    /**
     * Deletes a device with the specified device ID.
     *
     * @param deviceId The ID of the device to delete.
     * @param callback The callback to handle the result of the operation.
     */
    public void deleteDevice(String deviceId, FirebaseAllDevicesCallback callback) {
        firebaseDeviceApi.deleteDevice(deviceId, callback);
    }

    /**
     * Updates the status of a device with the specified device ID on Firebase.
     *
     * @param deviceId The ID of the device to update.
     * @param newStatus The new status to set for the device.
     * @param callback The callback to handle the result of the operation.
     */
    public void updateDeviceStatus(String deviceId, String newStatus, FirebaseUpdateDeviceCallback callback) {
        firebaseDeviceApi.updateDeviceStatus(deviceId, newStatus, callback);
    }
}
