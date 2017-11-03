/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.zxdmjr.eatit.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 10/5/17.
 */

public class Database extends SQLiteAssetHelper {



    public Database(Context context) {
        super(context, DBConstant.DB.NAME, null, DBConstant.DB.VERSION);
    }

    public List<Order> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {
                DBConstant.TB.ORDERDETAIL.PRODUCT_NAME,
                DBConstant.TB.ORDERDETAIL.PRODUCT_ID,
                DBConstant.TB.ORDERDETAIL.PRODUCT_QUANTITY,
                DBConstant.TB.ORDERDETAIL.PRODUCT_PRICE,
                DBConstant.TB.ORDERDETAIL.PRODUCT_DISCOUNT,

        };

        qb.setTables(DBConstant.TB.ORDERDETAIL.NAME);

        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        List<Order> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                result.add(
                        new Order(
                                cursor.getString(cursor.getColumnIndex(DBConstant.TB.ORDERDETAIL.PRODUCT_ID)),
                                cursor.getString(cursor.getColumnIndex(DBConstant.TB.ORDERDETAIL.PRODUCT_NAME)),
                                cursor.getString(cursor.getColumnIndex(DBConstant.TB.ORDERDETAIL.PRODUCT_QUANTITY)),
                                cursor.getString(cursor.getColumnIndex(DBConstant.TB.ORDERDETAIL.PRODUCT_PRICE)),
                                cursor.getString(cursor.getColumnIndex(DBConstant.TB.ORDERDETAIL.PRODUCT_DISCOUNT))
                        )
                );


            } while(cursor.moveToNext());
        }

        return result;

    }

    public void addToCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(
                "INSERT INTO %s(%s,%s,%s,%s,%s) VALUES('%s','%s','%s','%s','%s');",
                DBConstant.TB.ORDERDETAIL.NAME,
                DBConstant.TB.ORDERDETAIL.PRODUCT_ID,
                DBConstant.TB.ORDERDETAIL.PRODUCT_NAME,
                DBConstant.TB.ORDERDETAIL.PRODUCT_QUANTITY,
                DBConstant.TB.ORDERDETAIL.PRODUCT_PRICE,
                DBConstant.TB.ORDERDETAIL.PRODUCT_DISCOUNT,
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount()
        );

        db.execSQL(query);

    }

    public void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM "+DBConstant.TB.ORDERDETAIL.NAME;
        db.execSQL(query);

    }

}
