package com.example.brianmote.billmanager.listener;

import com.google.firebase.database.DatabaseError;

/**
 * Created by Brian Mote on 6/13/2016.
 */
public interface DBGetDataListener<T> {
    void onComplete(T t);
    void onCancel(DatabaseError databaseError);
}
