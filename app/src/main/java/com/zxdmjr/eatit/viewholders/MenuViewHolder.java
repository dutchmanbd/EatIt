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
 * Created by dutchman on 10/5/17.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvMenuName;
    public ImageView ivMenuImage;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View view) {
        super(view);

        tvMenuName = (TextView) view.findViewById(R.id.tv_single_menu_name);
        ivMenuImage = (ImageView) view.findViewById(R.id.iv_single_menu_image);

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
