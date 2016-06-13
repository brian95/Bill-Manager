package com.example.brianmote.billmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.brianmote.billmanager.R;
import com.example.brianmote.billmanager.adapter.BillAdapter;
import com.example.brianmote.billmanager.helper.DBBillHelper;
import com.example.brianmote.billmanager.helper.FBAuthHelper;
import com.example.brianmote.billmanager.listener.DBGetDataListener;
import com.example.brianmote.billmanager.listener.FBAuthListener;
import com.example.brianmote.billmanager.model.Bill;
import com.example.brianmote.billmanager.utils.Constants;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private DBBillHelper billHelper;
    private RecyclerView billRView;
    private BillAdapter billAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateList();
    }

    private void populateList() {
        billRView = (RecyclerView) findViewById(R.id.billRView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        billAdapter = new BillAdapter(Bill.class, R.layout.bill_recycler_views, BillAdapter
                .BillHolder.class, FirebaseDatabase.getInstance().getReference().child(Constants
                .DB_BILL_REF));
        billRView.setLayoutManager(layoutManager);
        billRView.setAdapter(billAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_add_bill:
                startActivity(new Intent(HomeActivity.this, CreateBillActivity.class));
                return true;

            case R.id.action_logout:
                FBAuthHelper.logout();
                if (!FBAuthHelper.isLoggedIn()) {
                    Toast.makeText(HomeActivity.this, "Logging Out", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Logging out");
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
