package com.example.powermmanagementapplication.repository.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Interface defining methods for Firebase authentication operations.
 */
public interface FirebaseAuthApi {

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email    The email address of the user.
     * @param password The password for the user account.
     * @return A Task representing the asynchronous registration process.
     */
    Task<AuthResult> signUpWithEmailAndPassword(String email, String password);

    /**
     * Signs in an existing user with the provided email and password.
     *
     * @param email    The email address of the user.
     * @param password The password for the user account.
     * @return A Task representing the asynchronous sign-in process.
     */
    Task<AuthResult> signInWithEmailAndPassword(String email, String password);

    /**
     * Signs out the currently signed-in user.
     */
    void signOut();

    /**
     * Checks if a user is currently signed in.
     *
     * @return True if a user is signed in, false otherwise.
     */
    boolean isUserSignedIn();

    /**
     * Retrieves information about the currently signed-in user.
     *
     * @return The FirebaseUser object representing the current user, or null if no user is signed in.
     */
    FirebaseUser getCurrentUser();

}
