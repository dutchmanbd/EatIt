/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.activities;

import android.app.ProgressDialog;
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
import com.zxdmjr.eatit.R;
import com.zxdmjr.eatit.models.User;

import info.hoang8f.widget.FButton;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialEditText etPhoneNumber, etName, etPassword;
    private FButton btnSignUp;
    private TextView tvLogin;

    private DatabaseReference userTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initializeView();
    }

    private void initializeView() {

        etPhoneNumber = (MaterialEditText) findViewById(R.id.et_phone_number);
        etName = (MaterialEditText) findViewById(R.id.et_name);
        etPassword = (MaterialEditText) findViewById(R.id.et_password);

        btnSignUp = (FButton) findViewById(R.id.btn_sign_up);

        tvLogin = (TextView) findViewById(R.id.tv_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userTable = database.getReference("User");


        btnSignUp.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_sign_up:

                if(etPhoneNumber.getText().toString().trim().length() > 0 && etName.getText().toString().trim().length() > 0 && etPassword.getText().toString().length() > 0)
                    doRegister();
                else
                    Toast.makeText(getApplicationContext(), "Fill all the field", Toast.LENGTH_SHORT).show();

                break;

            case R.id.tv_login:

                break;
        }

    }

    private void doRegister() {

        if(userTable != null){
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading....");
            mProgressDialog.show();

            userTable.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();

                    String phoneNumber = etPhoneNumber.getText().toString().trim();
                    String name = etName.getText().toString().trim();
                    String password = etPassword.getText().toString();

                    if(dataSnapshot.child(phoneNumber).exists()){
                        Toast.makeText(getApplicationContext(), "Phone number already exist", Toast.LENGTH_SHORT).show();
                    } else{
                        User user = new User(name, password);

                        userTable.child(phoneNumber).setValue(user);

                        Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                        finish();

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
