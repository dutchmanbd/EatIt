/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.interfaces.ItemClickListener;

/**
 * Created by dutchman on 10/6/17.
 */

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvCartName;
    public TextView tvCartPrice;
    public ImageView ivCartQuantity;

    private ItemClickListener itemClickListener;

    public CartViewHolder(View view) {
        super(view);

        tvCartName = (TextView) view.findViewById(R.id.tv_cart_item_name);
        tvCartPrice = (TextView) view.findViewById(R.id.tv_cart_item_price);

        ivCartQuantity = (ImageView) view.findViewById(R.id.iv_cart_quantity);

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
