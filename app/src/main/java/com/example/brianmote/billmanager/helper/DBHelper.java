package com.example.brianmote.billmanager.helper;

import com.example.brianmote.billmanager.listener.DBCompleteListener;
import com.example.brianmote.billmanager.listener.DBGetDataListener;

import java.util.ArrayList;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public interface DBHelper<T> {
    void create(T t, final DBCompleteListener dbCompleteListener);

    void getById(String id, final DBGetDataListener<T> dbGetDataListener);

    ArrayList<T> getAll(final DBGetDataListener<T> dbGetDataListener);
}
