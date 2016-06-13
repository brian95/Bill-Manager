package com.example.brianmote.billmanager.listener;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public interface FBAuthListener {
    void onAuthChange(@NonNull FirebaseAuth firebaseAuth);
}
