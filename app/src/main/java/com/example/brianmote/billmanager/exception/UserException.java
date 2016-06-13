package com.example.brianmote.billmanager.exception;

import com.example.brianmote.billmanager.model.User;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public class UserException extends Exception {

    public UserException() {

    }

    public UserException(String message) {
        super(message);
    }
}
