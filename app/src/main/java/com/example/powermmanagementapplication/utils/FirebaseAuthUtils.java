package com.example.powermmanagementapplication.utils;

import com.example.powermmanagementapplication.repository.api.FirebaseAuthApi;

public class FirebaseAuthUtils {

    public static boolean isUserAuthenticated(FirebaseAuthApi fireBaseAuthApi) {
        return fireBaseAuthApi.isUserSignedIn();
    }

}
