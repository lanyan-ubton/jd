package com.own.jd.entity;

import lombok.Data;

@Data
public class order {
    private String orderid;
    private String userid;
    private String shoppingid;
    private double payment;
    private int paymenttype;
    private int postage;
    private int status;
    private String paymenttime;
    private String sendtime;
    private String endtime;
    private String closeTime;
}
