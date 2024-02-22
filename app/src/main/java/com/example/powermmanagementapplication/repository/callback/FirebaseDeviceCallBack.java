package com.example.powermmanagementapplication.repository.callback;

import com.example.powermmanagementapplication.model.device.Device;

public interface FirebaseDeviceCallBack {

    void onDeviceDataRetrieved(Device device);

    void onDeviceDataRetrievalFailure(String errorMessage);

}
