package com.example.brianmote.billmanager.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brianmote.billmanager.R;
import com.example.brianmote.billmanager.adapter.BillAdapter;
import com.example.brianmote.billmanager.helper.DBBillHelper;
import com.example.brianmote.billmanager.helper.DBHelper;
import com.example.brianmote.billmanager.listener.DBCompleteListener;
import com.example.brianmote.billmanager.model.Bill;
import com.example.brianmote.billmanager.utils.Utils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class CreateBillActivity extends AppCompatActivity {
    private static final String TAG = CreateBillActivity.class.getSimpleName();
    private EditText cBillName;
    private EditText cBillAmount;
    private Bill bill;
    private DBHelper billHelper;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_create_bill:
                createBill();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createBill() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(CreateBillActivity.this);
        }
        Utils.configProgDialog(progressDialog);

        if (cBillName == null) {
            cBillName = (EditText)findViewById(R.id.cBillName);
        }
        if (cBillAmount == null) {
            cBillAmount = (EditText)findViewById(R.id.cBillAmount);
        }
        String name = cBillName.getText().toString();
        String amount = cBillAmount.getText().toString();

        if (Utils.isStringEmpty(name)) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Toast.makeText(CreateBillActivity.this, "Please Enter the Bill Name",
                    Toast.LENGTH_SHORT).show();
        } else if (Utils.isStringEmpty(amount)) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(CreateBillActivity.this, "Please Enter the Amount of the Bill",
                    Toast.LENGTH_SHORT).show();
        } else {
            bill = new Bill(name, amount);
        }

        if (bill != null) {
            billHelper = new DBBillHelper();
            billHelper.create(bill, new DBCompleteListener() {
                @Override
                public void onComplete(DatabaseError databaseError,
                                       DatabaseReference databaseReference) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (databaseError == null) {
                        Log.d(TAG, "Created Bill");
                        Log.d(TAG, "Name: " + bill.getName());
                        Log.d(TAG, "Amount: " + bill.getAmount());
                        Toast.makeText(CreateBillActivity.this, "Bill Created",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
