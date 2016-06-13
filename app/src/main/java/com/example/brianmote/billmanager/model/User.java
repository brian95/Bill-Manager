package com.example.brianmote.billmanager.model;

import android.support.annotation.NonNull;

import com.example.brianmote.billmanager.utils.Constants;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brian Mote on 6/12/2016.
 */
@IgnoreExtraProperties
public class User {
    private String email;
    private String pass;
    private String id;

    private User() {
        //Required by Firebase
    }

    public User(@NonNull String email) {
        this.email = email;
    }

    public User(@NonNull String email, @NonNull String pass) {
        this(email);
        this.pass = pass;
    }

    public User(@NonNull String email, @NonNull String pass, @NonNull String id) {
        this(email, pass);
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.USER_EMAIL, email);
        map.put(Constants.USER_ID, id);
        return map;
    }
}
