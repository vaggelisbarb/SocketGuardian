package com.example.powermmanagementapplication.model.device;

import java.io.Serializable;

public class Settings implements Serializable {

    private int doorHeight;
    private int childrenHeight;

    public Settings() {}

    public Settings(int doorHeight, int childrenHeight) {
        this.doorHeight = doorHeight;
        this.childrenHeight = childrenHeight;
    }

    public int getDoorHeight() {
        return doorHeight;
    }

    public void setDoorHeight(int doorHeight) {
        this.doorHeight = doorHeight;
    }

    public int getChildrenHeight() {
        return childrenHeight;
    }

    public void setChildrenHeight(int childrenHeight) {
        this.childrenHeight = childrenHeight;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "doorHeight=" + doorHeight +
                ", childrenHeight=" + childrenHeight +
                '}';
    }
}
