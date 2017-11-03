/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dutchman on 10/5/17.
 */

public class Food {
    @SerializedName("Name")
    private String Name;
    @SerializedName("Image")
    private String Image;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Price")
    private String Price;
    @SerializedName("Discount")
    private String Discount;
    @SerializedName("MenuId")
    private String MenuId;

    public Food() {
    }

    public Food(String name, String image, String description, String price, String discount, String menuId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
