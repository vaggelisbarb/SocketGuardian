package com.example.powermmanagementapplication.repository.callback;

import com.example.powermmanagementapplication.model.device.PowerSocket;

public interface FirebaseUpdatePowerSocketCallback {

    /**
     * Called when the power socket status update operation is successful.
     */
    void onPowerSocketStatusUpdateSuccess();

    /**
     * Called when the power socket status update operation fails.
     *
     * @param errorMessage The error message indicating the cause of the failure.
     */
    void onPowerSocketStatusUpdateFailure(String errorMessage);

}
