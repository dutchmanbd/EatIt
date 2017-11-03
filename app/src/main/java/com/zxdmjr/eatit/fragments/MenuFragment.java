/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.activities.FoodActivity;
import com.zxdmjr.eatit.common.Constant;
import com.zxdmjr.eatit.interfaces.ItemClickListener;
import com.zxdmjr.eatit.models.Category;
import com.zxdmjr.eatit.viewholders.MenuViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    private View view;

    private RecyclerView rvMenu;

    private FirebaseDatabase database;
    private DatabaseReference category;

    private FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        initializeView();

        return view;
    }

    private void initializeView(){

        rvMenu = (RecyclerView) view.findViewById(R.id.rv_menu);


        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(new LinearLayoutManager(getContext()));

        //init firebase database
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        loadMenu();

    }

    private void loadMenu(){

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.single_menu_item, MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.tvMenuName.setText(model.getName());

                Picasso.with(getContext()).load(model.getImage()).into(viewHolder.ivMenuImage);

                final Category clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getContext(), clickItem.getName(), Toast.LENGTH_SHORT).show();
                        //Get Category Id and send it to FoodActivity
                        Intent intent = new Intent(getContext(), FoodActivity.class);
                        //Because Category Id is key, so we just get key of this item
                        intent.putExtra(Constant.INTENT_CATEGORY_ID, adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }
        };

        rvMenu.setAdapter(adapter);


    }

}
