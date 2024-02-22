package com.example.powermmanagementapplication.repository.firebase;

import com.example.powermmanagementapplication.repository.api.FirebaseAuthApi;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthImpl implements FirebaseAuthApi {

    private FirebaseAuth firebaseAuth;

    public FirebaseAuthImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Task<AuthResult> signUpWithEmailAndPassword(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }

    public boolean isUserSignedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
