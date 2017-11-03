/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.interfaces.ItemClickListener;

/**
 * Created by dutchman on 10/6/17.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvOrderId, tvOrderStatus, tvOrderPhone, tvOrderAddress;
    private ItemClickListener itemClickListener;


    public OrderViewHolder(View view) {
        super(view);

        tvOrderId = (TextView) view.findViewById(R.id.tv_order_id);
        tvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
        tvOrderPhone = (TextView) view.findViewById(R.id.tv_order_phone);
        tvOrderAddress = (TextView) view.findViewById(R.id.tv_order_address);

        view.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
