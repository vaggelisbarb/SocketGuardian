package com.example.powermmanagementapplication.model.device;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class PowerSocket implements Serializable {

    private String socketId;
    private String socketName;
    private String status;

    public PowerSocket() { }

    public PowerSocket(String socketID, String socketName, String status) {
        this.socketId = socketID;
        this.socketName = socketName;
        this.status = status;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getSocketName() {
        return socketName;
    }

    public void setSocketName(String socketName) {
        this.socketName = socketName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toogleStatus() {
        if (this.status.equalsIgnoreCase("On"))
            this.status = "Off";
        else if (this.status.equalsIgnoreCase("Off"))
            this.status = "On";
        return getStatus();
    }

    @Override
    public String toString() {
        return "PowerSocket{" +
                "socketId='" + socketId + '\'' +
                ", socketName='" + socketName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
