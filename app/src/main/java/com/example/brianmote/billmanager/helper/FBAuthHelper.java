package com.example.brianmote.billmanager.helper;

import android.support.annotation.NonNull;

import com.example.brianmote.billmanager.listener.FBAuthListener;
import com.example.brianmote.billmanager.listener.FBCompleteListener;
import com.example.brianmote.billmanager.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Brian Mote on 6/12/2016.
 */
public class FBAuthHelper {
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private FBCompleteListener fbCompleteListener;
    private FBAuthListener fbAuthListener;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DBHelper<User> fbUserHelper;
    private User user;

    public FBAuthHelper() {

    }

    public void register(final @NonNull User user, @NonNull final FBCompleteListener
            fbCompleteListener) {
        this.fbCompleteListener = fbCompleteListener;
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPass())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user.setId(authResult.getUser().getUid());
                        fbCompleteListener.onSuccess(authResult);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                fbCompleteListener.onFail(e);
            }
        });
    }

    public void login(@NonNull User user, @NonNull final FBCompleteListener fbCompleteListener) {
        this.fbCompleteListener = fbCompleteListener;
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPass())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        fbCompleteListener.onSuccess(authResult);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fbCompleteListener.onFail(e);
                    }
                });
    }

    public static boolean isLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void logout() {
        auth.signOut();
    }
}
