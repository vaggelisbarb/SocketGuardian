package com.example.powermmanagementapplication.repository.callback;


import com.example.powermmanagementapplication.model.device.PowerSocket;

public interface FirebasePowerSocketCallback {

    void onPowerSocketDataRetrieved(PowerSocket powerSocket);

    void onPowerSocketDataRetrievalFailure(String errorMessage);

}
