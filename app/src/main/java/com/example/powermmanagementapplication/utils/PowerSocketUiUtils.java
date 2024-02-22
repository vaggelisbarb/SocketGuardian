package com.example.powermmanagementapplication.utils;

public class PowerSocketUiUtils {

    public static String generateStatusIcon(String status) {
        if (status.equalsIgnoreCase("On"))
            return "green_electricity";
        else if (status.equalsIgnoreCase("Off"))
            return "red_electricity";
        else
            return "red_electricity";
    }

}
