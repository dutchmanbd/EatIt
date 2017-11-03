/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.zxdmjr.eatit.models;

import java.util.List;

/**
 * Created by dutchman on 10/6/17.
 */

public class Request {

    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private List<Order> orders; //List of food order

    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> orders) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.orders = orders;
        this.status = "0"; //Default is 0. 0: Placed, 1: Shipping, 2: Shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
