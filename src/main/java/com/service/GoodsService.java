package com.service;

import java.util.List;

import com.bean.Goods;

public interface GoodsService 
{
	 
	 boolean update(Goods goods);//更新商品信息
	 List<Goods> getGoods();//获取全部在售的商品
	 Goods getGoods(String GoodsId);//获取指定商品信息
	 String addGoods(Goods goods);//商家添加商品
	 boolean deleteGoods(String goodsId);
}
