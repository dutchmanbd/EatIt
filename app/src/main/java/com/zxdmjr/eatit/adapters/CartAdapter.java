/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.interfaces.ItemClickListener;
import com.zxdmjr.eatit.models.Order;
import com.zxdmjr.eatit.viewholders.CartViewHolder;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by dutchman on 10/6/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private Context context;
    private List<Order> orders;


    public CartAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_cart_item, parent, false);

        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {

        final Order order = orders.get(position);

        TextDrawable textDrawable = TextDrawable.builder().buildRound(order.getQuantity(), Color.RED);

        holder.ivCartQuantity.setImageDrawable(textDrawable);

        Locale locale = new Locale("en","US");

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        double price = Double.parseDouble(order.getPrice()) * Double.parseDouble(order.getQuantity());

        holder.tvCartPrice.setText(fmt.format(price));

        holder.tvCartName.setText(order.getProductName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {



            }
        });



    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
