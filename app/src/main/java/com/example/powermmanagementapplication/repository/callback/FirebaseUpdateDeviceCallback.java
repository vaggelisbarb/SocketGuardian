package com.example.powermmanagementapplication.repository.callback;

/**
 * Interface defining callback methods to handle the result of device status update operations performed asynchronously.
 */
public interface FirebaseUpdateDeviceCallback {
    /**
     * Called when the device status update operation is successful.
     */
    void onDeviceStatusUpdateSuccess();

    /**
     * Called when the device status update operation fails.
     *
     * @param errorMessage The error message describing the failure.
     */
    void onDeviceStatusUpdateFailure(String errorMessage);
}
