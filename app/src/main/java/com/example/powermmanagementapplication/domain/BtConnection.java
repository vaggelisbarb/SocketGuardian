package com.example.powermmanagementapplication.domain;

import android.bluetooth.BluetoothAdapter;

public class BtConnection {

    private boolean state;

    public BtConnection() {
        this.state = false;
    }

    public BtConnection(boolean state) {
        this.state = state;
    }

    public boolean getBluetoothState(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled())
            this.state = true;
        else
            this.state = false;

        return getState();
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
