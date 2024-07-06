package com.own.jd.entity;

import lombok.Data;

@Data
public class Cart {
    private String carid;
    private String proid;
    private String userid;
    private int quantity;
    private int checked;
}
