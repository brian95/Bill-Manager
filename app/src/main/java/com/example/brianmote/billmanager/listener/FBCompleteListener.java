package com.example.brianmote.billmanager.listener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public interface FBCompleteListener {
    void onSuccess(AuthResult authResult);
    void onFail(Exception e);
}
