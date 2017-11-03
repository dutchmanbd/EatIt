/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.adapters.CartAdapter;
import com.zxdmjr.eatit.common.Common;
import com.zxdmjr.eatit.databases.Database;
import com.zxdmjr.eatit.models.Order;
import com.zxdmjr.eatit.models.Request;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class CartActivity extends AppCompatActivity {


    private RecyclerView rvCartList;
    private FButton btnPlaceOrder;
    private TextView tvCartTotal;

    private FirebaseDatabase database;
    private DatabaseReference requests;

    private CartAdapter adapter;
    private List<Order> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeView();
    }

    private void initializeView() {

        rvCartList = (RecyclerView) findViewById(R.id.rv_cart_list);
        btnPlaceOrder = (FButton) findViewById(R.id.btn_place_order);
        tvCartTotal = (TextView) findViewById(R.id.tv_cart_total);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        rvCartList.setHasFixedSize(true);
        rvCartList.setLayoutManager(new LinearLayoutManager(this));

        loadCartList();

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(orders != null && orders.size() > 0)
                    showAlertDialog();
                else
                    Toast.makeText(getApplicationContext(), "No food to place", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);

        //Text Title
        TextView title = new TextView(CartActivity.this);
        title.setText("Add More Step");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.colorAccent));
        title.setTextSize(22);

        //alertDialog.setTitle(cName);
        alertDialog.setCustomTitle(title);

        LayoutInflater inflater = getLayoutInflater();
        View view = (View) inflater.inflate(R.layout.custom_place_order_alert, null);

        TextView tvPhoneNumber, tvName, tvPrice;
        final MaterialEditText etAddress;
        RecyclerView rvCustomCartList;
        Button btnOk, btnCancel;

        tvPhoneNumber = (TextView) view.findViewById(R.id.custom_phone_number);
        tvName = (TextView) view.findViewById(R.id.custom_name);
        tvPrice = (TextView) view.findViewById(R.id.custom_total_price);

        etAddress = (MaterialEditText) view.findViewById(R.id.et_custom_address);

        rvCustomCartList = (RecyclerView) view.findViewById(R.id.rv_custom_cart_list);

        btnOk = (Button) view.findViewById(R.id.btn_custom_ok);
        btnCancel = (Button) view.findViewById(R.id.btn_custom_cancel);

        rvCustomCartList.setHasFixedSize(true);
        rvCustomCartList.setLayoutManager(new LinearLayoutManager(this));

        tvName.setText(Common.currentUser.getName());
        tvPhoneNumber.setText(Common.currentUser.getPhone());
        tvPrice.setText(tvCartTotal.getText().toString());

        rvCustomCartList.setAdapter(adapter);

        alertDialog.setView(view);



//        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                if(etAddress.getText().toString().trim().length() > 0){
//
//                    Request request = new Request(
//
//                            Common.currentUser.getPhone(),
//                            Common.currentUser.getName(),
//                            etAddress.getText().toString().trim(),
//                            tvCartTotal.getText().toString().trim(),
//                            orders
//                    );
//
//                    //Submit to firebase
//                    //We will using System.CurrentMili to key
//                    requests.child(String.valueOf(System.currentTimeMillis())).
//                            setValue(request);
//
//                    //Delete Cart
//                    new Database(getBaseContext()).clearCart();
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(), "Thank you, Place Order", Toast.LENGTH_SHORT).show();
//                    finish();
//
//                } else{
//                    Toast.makeText(getApplicationContext(), "Please Enter your address", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                dialogInterface.dismiss();
//            }
//        });

        alertDialog.setCancelable(false);
        final AlertDialog dialog = alertDialog.create();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etAddress.getText().toString().trim().length() > 0){

                    Request request = new Request(

                            Common.currentUser.getPhone(),
                            Common.currentUser.getName(),
                            etAddress.getText().toString().trim(),
                            tvCartTotal.getText().toString().trim(),
                            orders
                    );

                    //Submit to firebase
                    //We will using System.CurrentMili to key
                    requests.child(String.valueOf(System.currentTimeMillis())).
                            setValue(request);

                    //Delete Cart
                    new Database(getBaseContext()).clearCart();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Thank you, Place Order", Toast.LENGTH_SHORT).show();
                    finish();

                } else{
                    Toast.makeText(getApplicationContext(), "Please Enter your address", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//        dialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if(etAddress.getText().toString().trim().length() > 0){
//
//                    Request request = new Request(
//
//                            Common.currentUser.getPhone(),
//                            Common.currentUser.getName(),
//                            etAddress.getText().toString().trim(),
//                            tvCartTotal.getText().toString().trim(),
//                            orders
//                    );
//
//                    //Submit to firebase
//                    //We will using System.CurrentMili to key
//                    requests.child(String.valueOf(System.currentTimeMillis())).
//                            setValue(request);
//
//                    //Delete Cart
//                    new Database(getBaseContext()).clearCart();
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(getApplicationContext(), "Thank you, Place Order", Toast.LENGTH_SHORT).show();
//                    finish();
//
//                } else{
//                    Toast.makeText(getApplicationContext(), "Please Enter your address", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        dialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();

    }

    private void loadCartList(){

        orders = new Database(this).getCarts();

        adapter = new CartAdapter(this, orders);

        rvCartList.setAdapter(adapter);

        //calculate total price

        double totalPrice = 0.0;

        for(Order order : orders)
            totalPrice += (Double.parseDouble(order.getPrice()) * Double.parseDouble(order.getQuantity()));

        Locale locale = new Locale("en","US");

        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        tvCartTotal.setText(fmt.format(totalPrice));


    }
}
