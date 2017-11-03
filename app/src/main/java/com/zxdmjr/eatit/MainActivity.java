package com.zxdmjr.eatit;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zxdmjr.eatit.activities.AbsRuntimePermission;
import com.zxdmjr.eatit.activities.SignUpActivity;

import info.hoang8f.widget.FButton;

public class MainActivity extends AbsRuntimePermission implements View.OnClickListener{


    private FButton btnSignIn, btnSignUp;
    private TextView tvSlogan;

    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = {Manifest.permission.INTERNET};
        requestAppPermissions(permissions, R.string.permission_message, REQUEST_CODE);


        initializeView();

    }

    @Override
    public void onPermissoinGranted(int requestCode) {

    }

    private void initializeView(){

        btnSignIn = (FButton) findViewById(R.id.btnSignIn);
        btnSignUp = (FButton) findViewById(R.id.btnSignUp);
        tvSlogan  = (TextView) findViewById(R.id.tv_slogan);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/nabila.ttf");

        tvSlogan.setTypeface(face);


        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignIn:
                gotoSignInActivity();
                break;

            case R.id.btnSignUp:
                gotoSignUpActivity();
                break;

        }

    }

    private void gotoSignInActivity(){
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        finish();
    }

    private void gotoSignUpActivity() {

        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}
