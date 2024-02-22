package com.example.powermmanagementapplication.model.device;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Detection implements Serializable {

    private int adults;
    private int children;

    public Detection() { }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public String getAdultsDetected() {
        return String.valueOf(adults);
    }

    public String getChildrenDetected() {
        return String.valueOf(children);
    }

    @Override
    public String toString() {
        return "Detection{" +
                "adults=" + adults +
                ", children=" + children +
                '}';
    }

}
