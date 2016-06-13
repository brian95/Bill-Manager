package com.example.brianmote.billmanager.utils;

import android.app.ProgressDialog;

import com.example.brianmote.billmanager.exception.UserException;
import com.example.brianmote.billmanager.model.User;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public class Utils {

    private Utils() {
        //No Instance
    }

    public static boolean isStringEmpty(String s) {
        if (s == null) {
            return true;
        } else if (s.equals("") || s.equals(" ")) {
            return true;
        } else if (s.length() < 1) {
            return true;
        } else {
            return false;
        }
    }

    public static void configProgDialog(ProgressDialog dialog) {
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
