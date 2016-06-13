package com.example.brianmote.billmanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brianmote.billmanager.R;
import com.example.brianmote.billmanager.helper.DBHelper;
import com.example.brianmote.billmanager.helper.FBAuthHelper;
import com.example.brianmote.billmanager.helper.DBUserHelper;
import com.example.brianmote.billmanager.listener.DBCompleteListener;
import com.example.brianmote.billmanager.utils.Utils;
import com.example.brianmote.billmanager.listener.FBCompleteListener;
import com.example.brianmote.billmanager.model.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private User user;
    private EditText regEmail;
    private EditText regPass;
    private FBAuthHelper authHelper;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (authHelper == null) {
            authHelper = new FBAuthHelper();
        }

        regEmail = (EditText) findViewById(R.id.regEmail);
        regPass = (EditText) findViewById(R.id.regPass);
        Button subBtn = (Button) findViewById(R.id.regSubBtn);

        if (subBtn != null) {
            subBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register();
                }
            });
        }
    }


    private void register() {
        if (dialog == null) {
            dialog = new ProgressDialog(RegisterActivity.this);
        }
        Utils.configProgDialog(dialog);

        String email = regEmail.getText().toString();
        String pass = regPass.getText().toString();

        if (Utils.isStringEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
        } else if (pass.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password Must Contain 6 or More Characters",
                    Toast.LENGTH_SHORT).show();
        } else {
            user = new User(email, pass);
        }

        if (user != null) {
            authHelper.register(user, new FBCompleteListener() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    user.setId(authResult.getUser().getUid());
                    createUser(user);

                    Log.d(TAG, "Created Account!");
                    Log.d(TAG, "UID: " + user.getId());
                    Log.d(TAG, "Email: " + authResult.getUser().getEmail());
                    Log.d(TAG, "Password: " + user.getPass());

                    if (FBAuthHelper.isLoggedIn()) {
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    }
                }

                @Override
                public void onFail(Exception e) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(RegisterActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void createUser(User user) {
        DBHelper<User> userHelper = new DBUserHelper();
        userHelper.create(user, new DBCompleteListener() {
            @Override
            public void onComplete(DatabaseError databaseError,
                                   DatabaseReference databaseReference) {
                Toast.makeText(RegisterActivity.this, "Account Created",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
