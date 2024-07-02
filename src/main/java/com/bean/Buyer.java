package com.bean;

//买家
public class Buyer extends User {
    private String myGoodsIds;// 购物车
    private String sex;// 性别
	public String getMyGoodsIds() {
		return myGoodsIds;
	}

	public void setMyGoodsIds(String myGoodsIds) {
		this.myGoodsIds = myGoodsIds;
	}

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
