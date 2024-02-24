package com.example.powermmanagementapplication.model.device;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Map;

@IgnoreExtraProperties
public class Device implements Serializable{

    private String deviceId;
    private String deviceName;
    private String connectivity;
    private String lastUpdate;
    private String pairStatus;
    private String deviceStatus;
    private Detection detection;
    private Map<String, PowerSocket> powerSockets;
    private Settings settings;

    public Device() { }

    public Device(String deviceId, String deviceName, String connectivity, String lastUpdate, String pairStatus, String deviceStatus) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.connectivity = connectivity;
        this.lastUpdate = lastUpdate;
        this.pairStatus = pairStatus;
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getPairStatus() {
        return pairStatus;
    }

    public void setPairStatus(String pairStatus) {
        this.pairStatus = pairStatus;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Detection getDetection() {
        return detection;
    }

    public void setDetection(Detection detection) {
        this.detection = detection;
    }

    public Map<String, PowerSocket> getPowerSockets() {
        return powerSockets;
    }

    public void setPowerSockets(Map<String, PowerSocket> powerSockets) {
        this.powerSockets = powerSockets;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }


    public String toggleStatus() {
        if (this.deviceStatus.equalsIgnoreCase("enabled"))
            this.deviceStatus = "disabled";
        else if (this.deviceStatus.equalsIgnoreCase("disabled"))
            this.deviceStatus = "enabled";
        return getDeviceStatus();
    }

    public String convertPowerSocketListToString(Map<String, ?> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : map.keySet()) {
            mapAsString.append(key + "=" + map.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", connectivity='" + connectivity + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", pairStatus='" + pairStatus + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", detection=" + detection.toString() +
                ", powerSockets=" + convertPowerSocketListToString(powerSockets) +
                ", settings=" + settings.toString() +
                '}';
    }
}
