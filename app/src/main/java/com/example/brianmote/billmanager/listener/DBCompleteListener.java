package com.example.brianmote.billmanager.listener;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public interface DBCompleteListener {
    void onComplete(DatabaseError databaseError, DatabaseReference databaseReference);
}
