package com.example.powermmanagementapplication.repository;

import com.example.powermmanagementapplication.model.authentication.User;
import com.example.powermmanagementapplication.repository.api.FirebaseAuthApi;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Repository class responsible for handling user authentication operations.
 */
public class UserRepository {

    private FirebaseAuthApi fireBaseAuthApi; // Reference to the FirebaseAuthApi for authentication

    /**
     * Constructor to initialize the UserRepository with a FirebaseAuthApi instance.
     *
     * @param fireBaseAuthApi The FirebaseAuthApi instance for handling authentication.
     */
    public UserRepository(FirebaseAuthApi fireBaseAuthApi) {
        this.fireBaseAuthApi = fireBaseAuthApi;
    }

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email    The email address of the user.
     * @param password The password for the user account.
     * @return A Task representing the asynchronous registration process.
     */
    public Task<AuthResult> registerUser(String email, String password) {
        return fireBaseAuthApi.signUpWithEmailAndPassword(email, password);
    }

    /**
     * Signs in an existing user with the provided email and password.
     *
     * @param email    The email address of the user.
     * @param password The password for the user account.
     * @return A Task representing the asynchronous sign-in process.
     */
    public Task<AuthResult> loginUser(String email, String password) {
        return fireBaseAuthApi.signInWithEmailAndPassword(email, password);
    }

    /**
     * Signs out the currently signed-in user.
     */
    public void signOutUser() {
        fireBaseAuthApi.signOut();
    }

    /**
     * Retrieves information about the currently signed-in user.
     *
     * @return The User object representing the current user, or null if no user is signed in.
     */
    public User getCurrentUser() {
        FirebaseUser firebaseUser = fireBaseAuthApi.getCurrentUser();
        if (firebaseUser != null) {
            // If a FirebaseUser is available, create a corresponding User object
            User user = new User();
            user.setUserId(firebaseUser.getUid());
            user.setEmail(firebaseUser.getEmail());
            // Set other user properties as needed
            return user;
        }
        return null; // Return null if no user is signed in
    }

}
