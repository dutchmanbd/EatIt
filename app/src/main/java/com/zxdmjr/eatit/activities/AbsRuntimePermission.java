/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;

import com.zxdmjr.eatit.R;


/**
 * Created by dutchman on 8/15/17.
 */

public abstract class AbsRuntimePermission extends AppCompatActivity {


    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mErrorString = new SparseIntArray();


    }

    public abstract void onPermissoinGranted(int requestCode);

    public void requestAppPermissions(final String[] permissions, final int stringId, final int requestCode){

        mErrorString.put(requestCode, stringId);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermissions = false;

        for(String permissoin : permissions){

            permissionCheck += ContextCompat.checkSelfPermission(this, permissoin);

            showRequestPermissions = showRequestPermissions || ActivityCompat.shouldShowRequestPermissionRationale(this, permissoin);

        }

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){

            if(showRequestPermissions){

                Snackbar.make(findViewById(R.id.content_main), stringId, Snackbar.LENGTH_INDEFINITE)
                        .setAction("GRANT", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ActivityCompat.requestPermissions(AbsRuntimePermission.this, permissions, requestCode);

                            }
                        }).show();

            } else{
                ActivityCompat.requestPermissions(this, permissions, requestCode);
            }
        } else{
            onPermissoinGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int permissionCheck = PackageManager.PERMISSION_GRANTED;

        for(int permission : grantResults){
            permissionCheck += permission;
        }

        if((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED){
            onPermissoinGranted(requestCode);
        } else{
            //Display message when some dangerous permission not accept
            Snackbar.make(findViewById(R.id.content_main), mErrorString.get(requestCode), Snackbar.LENGTH_INDEFINITE)
                    .setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent();

                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:"+getPackageName()));
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                            startActivity(intent);

                        }
                    }).show();
        }

    }
}
