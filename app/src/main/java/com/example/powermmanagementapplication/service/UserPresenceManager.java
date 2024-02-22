package com.example.powermmanagementapplication.service;

import java.util.HashMap;
import java.util.Map;

public class UserPresenceManager {

    private static final long ONLINE_THRESHOLD_MS = 5 * 60 * 1000; // 5 minutes

    public static void updateUserActivity(String userId) {
        long currentTime = System.currentTimeMillis();
        Map<String, Object> activityUpdate = new HashMap<>();
        activityUpdate.put("lastActivityTime", currentTime);
        // Update the user's last activity time in the database
        // Example: DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        // userRef.updateChildren(activityUpdate);
    }

    public static boolean isUserOnline(long lastActivityTime) {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastActivityTime) < ONLINE_THRESHOLD_MS;
    }

}
