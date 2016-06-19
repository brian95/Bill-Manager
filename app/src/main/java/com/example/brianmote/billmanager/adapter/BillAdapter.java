package com.example.brianmote.billmanager.adapter;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.brianmote.billmanager.R;
import com.example.brianmote.billmanager.helper.DBBillHelper;
import com.example.brianmote.billmanager.helper.DBHelper;
import com.example.brianmote.billmanager.listener.DBCompleteListener;
import com.example.brianmote.billmanager.listener.DBGetDataListener;
import com.example.brianmote.billmanager.model.Bill;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Brian Mote on 6/13/2016.
 */
public class BillAdapter extends FirebaseRecyclerAdapter<Bill, BillAdapter.BillHolder> {
    private int modelLayout;
    private Class<BillHolder> viewHolderClass;
    private DatabaseReference ref;

    public BillAdapter(Class<Bill> modelClass, int modelLayout, Class<BillHolder> viewHolderClass,
                       DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
    }


    @Override
    protected void populateViewHolder(BillHolder viewHolder, final Bill model, final int position) {
        viewHolder.setTitle(model.getName());
        viewHolder.setAmount(model.getAmount());
        viewHolder.setCreatedBy(model.getCreatedBy());
        viewHolder.setStatus(model.getMonthPaid());
        viewHolder.billStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBBillHelper helper = new DBBillHelper();
                if (model.getMonthPaid()) {
                    helper.removePay(model, new DBCompleteListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError,
                                               DatabaseReference databaseReference) {
                            notifyItemChanged(position, model);
                        }
                    });
                } else {
                    helper.pay(model, new DBCompleteListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError,
                                               DatabaseReference databaseReference) {
                         notifyItemChanged(position, model);
                        }
                    });
                }
            }
        });
    }

    public static class BillHolder extends RecyclerView.ViewHolder {
        public static TextView billTitleView;
        public static TextView billAmountView;
        public static TextView billCreatedByView;
        public static Button billStatusView;

        public BillHolder(View itemView) {
            super(itemView);
            billTitleView = (TextView) itemView.findViewById(R.id.billTitleView);
            billAmountView = (TextView) itemView.findViewById(R.id.billAmountView);
            billCreatedByView = (TextView) itemView.findViewById(R.id.billCreatedByView);
            billStatusView = (Button) itemView.findViewById(R.id.billStatusView);
        }

        public void setTitle(String title) {
            billTitleView.setText(title);
        }

        public void setAmount(String amount) {
            billAmountView.setText(amount);
        }

        public void setCreatedBy(String createdBy) {
            billCreatedByView.setText(createdBy);
        }

        public void setStatus(boolean status) {
            String msg;
            if (!status) {
                msg = "Not Paid";
                billStatusView.setTextColor(Color.RED);
            } else {
                msg = "Paid";
                billStatusView.setTextColor(Color.GREEN);
            }
            billStatusView.setText(msg);
        }
    }
}
