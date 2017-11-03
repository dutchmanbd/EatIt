/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.databases;

/**
 * Created by dutchman on 10/5/17.
 */

public class DBConstant {

    public class DB{

        public static final String NAME = "EatItDB.db";
        public static final int VERSION = 1;


    }

    public class TB{

        public class ORDERDETAIL{

            public static final String NAME = "OrderDetail";

            public static final String PRODUCT_ID = "ProductId";
            public static final String PRODUCT_NAME = "ProductName";
            public static final String PRODUCT_QUANTITY = "Quantity";
            public static final String PRODUCT_PRICE = "Price";
            public static final String PRODUCT_DISCOUNT = "Discount";

        }

    }

}
