package com.example.powermmanagementapplication.domain;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class DeviceDomain implements Serializable {

    private String deviceID;
    private String deviceName;
    private String connectivity;
    private String lastUpdate;
    private Boolean isPaired;
    private Boolean isEnabled;
    private String picUrl;
    private String statusIcon;

    public DeviceDomain() {}
    public DeviceDomain(String deviceID, String connectivity) {
        this.deviceID = deviceID;
        this.connectivity = connectivity;
    }

    public DeviceDomain(String deviceID, String deviceName, String connectivity, String lastUpdate, Boolean isPaired, Boolean isEnabled) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.connectivity = connectivity;
        this.lastUpdate = lastUpdate;
        this.isPaired = isPaired;
        this.isEnabled = isEnabled;

        initIcons(connectivity, isEnabled);
    }

    public String initPairingStatus(){
        if (this.isPaired)
            return "Paired";
        else
            return "Unpaired";
    }

    public String initEnableStatus(){
        if (this.isEnabled)
            return "Enabled";
        else
            return "Disabled";
    }

    public String setConnectivityIcon(){
        if (connectivity.equals("Wi-Fi"))
            return "wifi";
        else
            return "bluetooth";
    }

    private void initIcons(String connectivity, Boolean isEnabled){

        if (connectivity.equals("Wi-Fi"))
            setPicUrl("socket_wifi");
        else if (connectivity.equals("Bluetooth"))
            setPicUrl("socket_bt");
        else
            setPicUrl("");

        if (isEnabled)
            setStatusIcon("correct");
        else
            setStatusIcon("disabled");
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Boolean getPaired() {
        return isPaired;
    }

    public void setPaired(Boolean paired) {
        isPaired = paired;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(String statusIcon) {
        this.statusIcon = statusIcon;
    }
}
