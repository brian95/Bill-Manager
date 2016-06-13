package com.example.brianmote.billmanager.model;

import android.support.annotation.NonNull;

import com.example.brianmote.billmanager.utils.Constants;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brian Mote on 6/13/2016.
 */
@IgnoreExtraProperties
public class Bill {
    private String name;
    private String id;
    private String amount;
    private String createdBy;
    private boolean monthPaid;

    private Bill() {
        //Required for Firebase
    }

    public Bill(@NonNull String name, String amount) {
        this.name = name;
        this.amount = amount;
        monthPaid = false;
    }

    public Bill(@NonNull String name, @NonNull String id, @NonNull String amount) {
        this(name, amount);
        this.id = id;
        monthPaid = false;
    }

    public Bill(@NonNull String name, @NonNull String id, @NonNull String amount, @NonNull String
            createdBy) {
        this(name, id, amount);
        this.createdBy = createdBy;
        monthPaid = false;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isMonthPaid() {
        return monthPaid;
    }

    public String getCreatedBy() {
        return createdBy;
    }


    public void setMonthPaid(boolean monthPaid) {
        this.monthPaid = monthPaid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.BILL_NAME, name);
        map.put(Constants.BILL_AMOUNT, amount);
        map.put(Constants.BILL_ID, id);
        map.put(Constants.BILL_CREATED_BY, createdBy);
        return map;
    }
}
