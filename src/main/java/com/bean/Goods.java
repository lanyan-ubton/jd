package com.bean;

// 商品
public class Goods {
    private String name;        // 商品名
    private byte[] picture;     // 图片
    private Double price;       // 价格
    private int count;          // 库存
    private String description; // 描述
    private String id;             // 唯一标识
    private String sellerName;	//谁在卖
    //评价
	public void setName(String name) {
        this.name = name;
    }

    public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getName() {
        return name;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}
