package com.own.jd.entity;

import lombok.Data;

@Data
public class merchandise {
    private String proid;
    private String cateid;
    private String name;
    private double price;
    private String subimages;
    private String detail;
    private int stock;
    private int status;
}
