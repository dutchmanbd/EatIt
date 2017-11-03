/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.common.Constant;
import com.zxdmjr.eatit.databases.Database;
import com.zxdmjr.eatit.models.Food;
import com.zxdmjr.eatit.models.Order;

public class FoodDetailActivity extends AppCompatActivity {

    private TextView tvFoodName,tvFoodPrice,tvDescription;
    private ImageView ivFoodImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fabCart;
    private ElegantNumberButton numberButton;

    private String foodId = "";

    private FirebaseDatabase database;
    private DatabaseReference foodRef;

    private Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // get intent

        if(getIntent() != null)
            foodId = getIntent().getStringExtra(Constant.INTENT_FOOD_ID);

        database = FirebaseDatabase.getInstance();
        foodRef = database.getReference("Foods");

        initializeView();
    }

    private void initializeView() {

        tvFoodName = (TextView) findViewById(R.id.tv_food_name);
        tvFoodPrice = (TextView) findViewById(R.id.tv_price);
        tvDescription = (TextView) findViewById(R.id.tv_description);

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        fabCart = (FloatingActionButton) findViewById(R.id.fabChart);

        ivFoodImage = (ImageView) findViewById(R.id.iv_food_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);

        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentFood != null){

                    new Database(getBaseContext()).addToCart(new Order(
                            foodId,
                            currentFood.getName(),
                            numberButton.getNumber(),
                            currentFood.getPrice(),
                            currentFood.getDiscount()
                    ));

                    Toast.makeText(getApplicationContext(), "Add to Cart", Toast.LENGTH_SHORT).show();

                }
            }
        });

        if(foodId != null && !foodId.isEmpty())
            getFoodDetail();

    }

    private void getFoodDetail() {

        foodRef.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentFood = dataSnapshot.getValue(Food.class);

                Picasso.with(getApplicationContext()).load(currentFood.getImage()).into(ivFoodImage);
                collapsingToolbarLayout.setTitle(currentFood.getName());

                tvFoodName.setText(currentFood.getName());
                tvFoodPrice.setText(currentFood.getPrice());
                tvDescription.setText(currentFood.getDescription());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
