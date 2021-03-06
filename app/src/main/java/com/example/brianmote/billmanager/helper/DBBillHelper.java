package com.example.brianmote.billmanager.helper;

import com.example.brianmote.billmanager.listener.DBCompleteListener;
import com.example.brianmote.billmanager.listener.DBGetDataListener;
import com.example.brianmote.billmanager.model.Bill;
import com.example.brianmote.billmanager.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Brian Mote on 6/13/2016.
 */
public class DBBillHelper implements DBHelper<Bill> {
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference billDB = db.child(Constants.DB_BILL_REF);
    private DBCompleteListener dbCompleteListener;
    private DBGetDataListener<Bill> dbGetDataListener;
    ArrayList<Bill> bills = new ArrayList<>();

    public DBBillHelper() {

    }

    @Override
    public void create(final Bill bill, final DBCompleteListener dbCompleteListener) {
        this.dbCompleteListener = dbCompleteListener;
        String id = billDB.push().getKey();
        String createdBy = DBUserHelper.getCurrentUser().getEmail();
        bill.setId(id);
        bill.setCreatedBy(createdBy);
        bill.setMonthPaid(false);
        Map<String, Object> map = bill.toMap();
        billDB.child(id).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                updateUserBills(bill);
                dbCompleteListener.onComplete(databaseError, databaseReference);
            }
        });
    }

    @Override
    public void getById(String id, final DBGetDataListener<Bill> dbGetDataListener) {
        this.dbGetDataListener = dbGetDataListener;
        DatabaseReference billRef = billDB.child(id);
        billRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                dbGetDataListener.onComplete(bill);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dbGetDataListener.onCancel(databaseError);
            }
        });
    }

    @Override
    public ArrayList<Bill> getAll(final DBGetDataListener<Bill> dbGetDataListener) {
        this.dbGetDataListener = dbGetDataListener;
        billDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                int index = 0;
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bills.get(index) == null) {
                    bills.add(index, bill);
                } else {
                    index++;
                    bills.add(index, bill);
                }
                dbGetDataListener.onComplete(bill);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Bill bill = dataSnapshot.getValue(Bill.class);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return bills;
    }

    public void pay(final Bill bill, final DBCompleteListener dbCompleteListener) {
        this.dbCompleteListener = dbCompleteListener;
        billDB.child(bill.getId()).child("monthPaid").setValue(true,
                new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                dbCompleteListener.onComplete(databaseError, databaseReference);
            }
        });
    }

    public void removePay(final Bill bill, final DBCompleteListener dbCompleteListener) {
        this.dbCompleteListener = dbCompleteListener;
        billDB.child(bill.getId()).child("monthPaid").setValue(false,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError,
                                           DatabaseReference databaseReference) {
                        dbCompleteListener.onComplete(databaseError, databaseReference);
                    }
                });
    }

    private void removeListener(DatabaseReference databaseReference, ValueEventListener
            valueEventListener) {
        databaseReference.removeEventListener(valueEventListener);
    }

    private void updateUserBills(Bill bill) {
        DatabaseReference userBills = db.child(Constants.DB_USER_REF).child(DBUserHelper
                .getCurrentUser().getId()).child(Constants.USER_BILLS);
        userBills.child(bill.getId()).setValue(true);
    }
}
