/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.common.Constant;
import com.zxdmjr.eatit.interfaces.ItemClickListener;
import com.zxdmjr.eatit.models.Category;
import com.zxdmjr.eatit.models.Food;
import com.zxdmjr.eatit.viewholders.FoodViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private RecyclerView rvFood;
    private FirebaseDatabase database;
    private DatabaseReference foodList;
    private String categoryId = "";


    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    //search functionality
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    private List<String> suggestList;
    private MaterialSearchBar msbSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        //get intent here
        if(getIntent() != null)
            categoryId = getIntent().getStringExtra(Constant.INTENT_CATEGORY_ID);

        suggestList = new ArrayList<>();

        initializeView();
    }

    private void initializeView() {

        rvFood = (RecyclerView) findViewById(R.id.rv_food);
        msbSearch = (MaterialSearchBar) findViewById(R.id.msb_search);
        msbSearch.setHint("Enter food name");

        rvFood.setHasFixedSize(true);
        rvFood.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        if(categoryId != null && !categoryId.isEmpty())
            loadFoodList();


        loadSuggest();

        msbSearch.setLastSuggestions(suggestList);
        msbSearch.setCardViewElevation(10);
        msbSearch.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //When user type text, we will change the suggest list
                List<String> suggest = new ArrayList<String>();

                for(String search : suggestList){

                    if (search.toLowerCase().contains(msbSearch.getText().toString().toLowerCase())){

                        suggest.add(search);

                    }

                }
                msbSearch.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        msbSearch.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is close
                // Restore original adapter
                if(!enabled)
                    rvFood.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                // when search finished
                //Show result for search adapter

                doSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void doSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.single_food_item,
                FoodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.tvFoodName.setText(model.getName());

                Log.d("FoodActivity", "populateViewHolder: "+model.getName());

                Picasso.with(getApplicationContext()).load(model.getImage()).into(viewHolder.ivFoodImage);

                final Food clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getApplicationContext(), clickItem.getName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), FoodDetailActivity.class);
                        intent.putExtra(Constant.INTENT_FOOD_ID, searchAdapter.getRef(position).getKey());
                        startActivity(intent);

                    }
                });
            }
        };

        rvFood.setAdapter(searchAdapter);
    }

    private void loadSuggest() {

        foodList.orderByChild("MenuId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postData : dataSnapshot.getChildren()){
                    Food food = postData.getValue(Food.class);
                    suggestList.add(food.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadFoodList() {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.single_food_item, FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId) ) {  //Like: select * from Foods where MenuId = ?
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.tvFoodName.setText(model.getName());

                Log.d("FoodActivity", "populateViewHolder: "+model.getName());

                Picasso.with(getApplicationContext()).load(model.getImage()).into(viewHolder.ivFoodImage);

                final Food clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getApplicationContext(), clickItem.getName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), FoodDetailActivity.class);
                        intent.putExtra(Constant.INTENT_FOOD_ID, adapter.getRef(position).getKey());
                        startActivity(intent);

                    }
                });

            }
        };

        rvFood.setAdapter(adapter);
    }
}
