package com.example.brianmote.billmanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brianmote.billmanager.R;
import com.example.brianmote.billmanager.helper.DBUserHelper;
import com.example.brianmote.billmanager.helper.FBAuthHelper;
import com.example.brianmote.billmanager.listener.FBCompleteListener;
import com.example.brianmote.billmanager.model.User;
import com.example.brianmote.billmanager.utils.Utils;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText loginEmail;
    private EditText loginPass;
    private Button loginSubBtn;
    private Button loginRegBtn;
    private FBAuthHelper authHelper;
    private User user;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPass = (EditText) findViewById(R.id.loginPass);
        loginSubBtn = (Button) findViewById(R.id.loginSubBtn);
        loginRegBtn = (Button) findViewById(R.id.loginRegBtn);

        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new ProgressDialog(LoginActivity.this);
                }
                Utils.configProgDialog(dialog);
                login();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Resuming");
        if (FBAuthHelper.isLoggedIn()) {
            Log.d(TAG, "User is already logged in");
            Log.d(TAG, "UID: " + DBUserHelper.getCurrentUser().getId());
            Log.d(TAG, "Email: " + DBUserHelper.getCurrentUser().getEmail());
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "Stopped");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void login() {
        String email = loginEmail.getText().toString();
        String pass = loginPass.getText().toString();

        if (authHelper == null) {
            authHelper = new FBAuthHelper();
        }
        //Checking if the email or pass is null/empty
        if (Utils.isStringEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Email is Empty", Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringEmpty(pass)) {
            Toast.makeText(LoginActivity.this, "Password is Empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            user = new User(email, pass);
        }

        if (user != null)
            authHelper.login(user, new FBCompleteListener() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Logging in");
                    Log.d(TAG, "UID: " + authResult.getUser().getUid());
                    Log.d(TAG, "Email: " + authResult.getUser().getEmail());
                    if (FBAuthHelper.isLoggedIn()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                }

                @Override
                public void onFail(Exception e) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
