package com.example.powermmanagementapplication.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Detection {

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
        return "PeopleInfo{" +
                "adults=" + adults +
                ", kids=" + children +
                '}';
    }

}
