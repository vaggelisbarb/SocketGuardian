package com.example.powermmanagementapplication.repository.callback;

import com.example.powermmanagementapplication.model.device.Device;

import java.util.List;

/**
 * Interface defining callback methods to handle results of device-related operations performed asynchronously.
 */
public interface FirebaseAllDevicesCallback {

    /**
     * Called when device data is successfully retrieved.
     *
     * @param devices List of devices retrieved from Firebase.
     */
    void onDevicesDataRetrieved(List<Device> devices);

    /**
     * Called when an error occurs during device data retrieval.
     *
     * @param errorMessage Error message describing the failure.
     */
    void onDevicesDataRetrievalFailure(String errorMessage);

}
