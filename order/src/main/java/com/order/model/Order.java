package com.order.model;

import java.util.Date;
import java.util.UUID;

public class Order {

    public enum Status {
        pending,
        approved,
        rejected

    }
    private String orderId = null;
    private String customerId = null;
    private String orderName = null;
    private String orderQty = null;
    private String orderPrice = null;


    private String reserveCash = null;
    Status status = Status.pending;
    private Date dateTime = null;

    public Order(String[] order, Status pending){
        this.customerId = (order[0].split("="))[1];
        this.orderId = (order[1].split("="))[1];
        this.orderName = (order[2].split("="))[1];
        this.orderQty = (order[3].split("="))[1];
        this.orderPrice = (order[4].split("="))[1];
        this.reserveCash =(order[5].split("="))[1];
        this.status = pending;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReserveCash() {
        return reserveCash;
    }


    public String getCustomerId() {
        return customerId;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }



}
