package com.service;

import java.util.List;

import com.bean.Buyer;
import com.bean.Goods;

public interface BuyerService 
{
    boolean save(Buyer saveUser);//注册
    boolean login(Buyer loginUser);//登录
    boolean update(Buyer newMsg);//更新
    List<Buyer> getBuyers();
	Buyer getBuyer(String name); //获取买家信息
	boolean addGoods(Buyer buyer,Goods goods);//购物车添加商品
	boolean deleteGoods(Buyer buyer,Goods goods);//购物车删除商品
	List<Goods> getGoods(Buyer buyer);
	boolean sale(Buyer buyer,int money);
}
