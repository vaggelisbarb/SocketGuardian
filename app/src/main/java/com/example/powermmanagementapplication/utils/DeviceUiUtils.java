package com.example.powermmanagementapplication.utils;

public class DeviceUiUtils {

    // Determine device's icon based on connectivity
    public static String generateDeviceIcon(String connectivity) {
        if (connectivity.equalsIgnoreCase("Wi-Fi"))
            return "socket_wifi";
        else if (connectivity.equalsIgnoreCase("Bluetooth"))
            return "socket_bt";
        else
            return "socket_wifi";
    }

    // Determine device's statusIcon based on isEnabled
    public static String generateStatusIcon(String status) {
        if (status.equalsIgnoreCase("enabled"))
            return "enabled";
        else if (status.equalsIgnoreCase("disabled"))
            return "disabled";
        else
            return "disabled";
    }

    public static String generateConnectivityIcon(String connectivity) {
        if (connectivity.equalsIgnoreCase("Wi-Fi"))
            return "wifi";
        else if (connectivity.equalsIgnoreCase("Bluetooth"))
            return "bluetooth";
        else
            return "socket_wifi";
    }

}
