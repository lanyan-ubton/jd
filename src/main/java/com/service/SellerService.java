package com.service;

import java.util.List;

import com.bean.Seller;

public interface SellerService {
    boolean save(Seller saveUser);// 注册商家

    boolean login(Seller loginUser);// 登录

    List<Seller> getSellers(); //

    Seller getSeller(String sellerName);// 获取店铺信息

    boolean update(Seller seller);

    boolean updateSeller(String sellerName, int num);// 修改商品库存和余额
}
