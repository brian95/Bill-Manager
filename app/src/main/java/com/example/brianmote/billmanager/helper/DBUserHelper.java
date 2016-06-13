package com.example.brianmote.billmanager.helper;

import com.example.brianmote.billmanager.listener.DBCompleteListener;
import com.example.brianmote.billmanager.listener.DBGetDataListener;
import com.example.brianmote.billmanager.model.User;
import com.example.brianmote.billmanager.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by Brian Mote on 6/12/2016.
 */
public class DBUserHelper implements DBHelper<User>{
    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userDB = db.child(Constants.DB_USER_REF);
    private DBCompleteListener dbCompleteListener;
    private DBGetDataListener<User> dbGetDataListener;

    public DBUserHelper(){

    }

    @Override
    public void create(User user, final DBCompleteListener dbCompleteListener) {
        this.dbCompleteListener = dbCompleteListener;
        Map<String, Object> map = user.toMap();
        userDB.child(user.getId()).updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                dbCompleteListener.onComplete(databaseError, databaseReference);
            }
        });
    }

    @Override
    public void getAll(DBGetDataListener<User> dbGetDataListener) {

    }

    @Override
    public void getById(String id, DBGetDataListener dbGetDataListener) {

    }

    public static User getCurrentUser() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        User currentUser = new User(fbUser.getEmail());
        currentUser.setId(fbUser.getUid());
        return currentUser;
    }

}
