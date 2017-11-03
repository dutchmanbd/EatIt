/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zxdmjr.eatit.activities.HomeActivity;
import com.zxdmjr.eatit.common.Common;
import com.zxdmjr.eatit.models.User;

import info.hoang8f.widget.FButton;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialEditText etPhoneNumber;
    private MaterialEditText etPassword;

    private TextView tvRegister;
    private FButton btnSignIn;

    private DatabaseReference userTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        initializeView();
    }

    private void initializeView(){

        etPhoneNumber = (MaterialEditText) findViewById(R.id.etPhoneNumber);
        etPassword = (MaterialEditText) findViewById(R.id.etPassword);

        tvRegister = (TextView) findViewById(R.id.tv_register);

        btnSignIn = (FButton) findViewById(R.id.btnSignIn);

        //init firebase database

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userTable = database.getReference("User");


        btnSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignIn:

                if(etPhoneNumber.getText().toString().trim().length() > 0 && etPassword.getText().toString().length() > 0)
                    checkUserAuth();

                break;

            case R.id.tv_register:

                break;

        }

    }

    private void checkUserAuth(){

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading....");
        mProgressDialog.show();

        if(userTable != null){

            userTable.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    String phoneNumber = etPhoneNumber.getText().toString().trim();
                    String password = etPassword.getText().toString();

                    if(dataSnapshot.child(phoneNumber).exists()) {

                        User user = dataSnapshot.child(phoneNumber).getValue(User.class);
                        user.setPhone(phoneNumber);

                        if (user.getPassword().equals(password)) {
                            //Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                            Common.currentUser = user;
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "User not exist.", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if(mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                }
            });
        }
    }
}
