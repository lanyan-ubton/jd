package com.bean;

//用户类--基类
public class User {
    // 注册用户时
    private String name;// 姓名
    private String pwd;// 密码
    private double money = 1000;// 余额,默认1000
    private byte[] headPortrait;// 头像,默认空

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public byte[] getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        this.headPortrait = headPortrait;
    }

}
