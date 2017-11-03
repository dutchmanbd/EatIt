/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.common.Common;
import com.zxdmjr.eatit.interfaces.ItemClickListener;
import com.zxdmjr.eatit.models.Request;
import com.zxdmjr.eatit.viewholders.OrderViewHolder;

public class OrderStatusActivity extends AppCompatActivity {

    private RecyclerView rvOrderStatus;

    private FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    private FirebaseDatabase database;
    private DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        initializeView();
    }

    private void initializeView() {

        rvOrderStatus = (RecyclerView) findViewById(R.id.rv_order_status);
        rvOrderStatus.setHasFixedSize(true);
        rvOrderStatus.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        loadOrders();

    }

    private void loadOrders() {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.single_order_status_item,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(Common.currentUser.getPhone())
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.tvOrderId.setText(adapter.getRef(position).getKey());   // Key
                viewHolder.tvOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.tvOrderPhone.setText(model.getPhone());
                viewHolder.tvOrderAddress.setText(model.getAddress());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });


            }
        };

        rvOrderStatus.setAdapter(adapter);

    }

    private String convertCodeToStatus(String status) {

        if(status.equals("0"))
            return "Placed";

        else if(status.equals("1"))
            return "On my way";

        else
            return "Shipped";

    }

}
