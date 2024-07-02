package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;

import com.bean.Goods;
import com.bean.Seller;
import com.service.GoodsService;
import com.service.SellerService;

@Controller
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SellerService sellerService;
    private static String userName = "";

    @RequestMapping("/login")
    public String login(@ModelAttribute("seller") Seller seller,
            HttpSession session, Model model) {
        if (sellerService.login(seller)) {
            userName = seller.getName();
            seller = sellerService.getSeller(userName);
            byte[] imageData = seller.getHeadPortrait();
            if (imageData != null) {
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                session.setAttribute("picture", base64Image);
            }
            session.setAttribute("userName", seller.getName());
            model.addAttribute("seller", sellerService.getSeller(userName));
            return "sellerMsg"; // 登录成功跳转的页面
        } else {
            model.addAttribute("error", "用户名或密码有误！");
            model.addAttribute("seller", seller);
            return "redirect:sellerLogin";
        }
    }

    @RequestMapping("/save")
    public String register(@ModelAttribute("seller") Seller seller, Model model) {
        if (sellerService.save(seller)) {
            model.addAttribute("userName", seller.getName());
            return "sellerLogin"; // 注册成功跳转的页面
        } else {
            model.addAttribute("error", "用户名或密码有误！");
            model.addAttribute("seller", seller);
            return "sellerLogin";
        }
    }

    @RequestMapping("/update")
    public String updateBuyerMsg(@ModelAttribute("seller") Seller seller,
            @RequestParam MultipartFile newImage,
            HttpSession session, Model model) {
        if (!newImage.isEmpty()) {
            try {
                seller.setHeadPortrait(newImage.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            seller.setHeadPortrait(sellerService.getSeller(userName).getHeadPortrait());
        }
        // 数据库更新
        sellerService.update(seller);
        seller = sellerService.getSeller(userName);
        if (sellerService.getSeller(userName).getHeadPortrait() != null)
            model.addAttribute("picture",
                    Base64.getEncoder().encodeToString(sellerService.getSeller(userName).getHeadPortrait()));
        // 返回视图名称
        return "sellerMeg";
    }

    @RequestMapping("/myGoods")
    public String goMyGoods(Model model) {
        List<Goods> myGoods = new ArrayList<Goods>();
        for (Goods goods : goodsService.getGoods()) {
            if (goods.getSellerName().equals(userName)) {
                myGoods.add(goods);
            }
        }
        model.addAttribute("seller", sellerService.getSeller(userName));
        model.addAttribute("myGoods", myGoods);
        return "sellerOnSale";
    }

    @RequestMapping("/addGoods")
    public String saveGoods(@ModelAttribute("goods") Goods goods,
            @RequestParam("image") MultipartFile picture, Model model) {
        if (!picture.isEmpty()) {
            try {
                goods.setPicture(picture.getBytes());
                goods.setSellerName(sellerService.getSeller(userName).getName());
                // 保存商品信息到数据库
                goodsService.addGoods(goods);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Goods> myGoods = new ArrayList<Goods>();
        for (Goods exitGoods : goodsService.getGoods()) {
            if (exitGoods.getSellerName().equals(userName)) {
                myGoods.add(exitGoods);
            }
        }
        model.addAttribute("seller", sellerService.getSeller(userName));
        model.addAttribute("myGoods", myGoods);
        return "sellerOnSale";
    }

    @RequestMapping("/updateGoods")
    public String updateGooos(@ModelAttribute("goods") Goods goods,
            @RequestParam MultipartFile newImage, Model model) {
        if (!newImage.isEmpty()) {
            try {
                goods.setPicture(newImage.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            goods.setPicture(goodsService.getGoods(goods.getId()).getPicture());
        }
        goodsService.update(goods);
        List<Goods> myGoods = new ArrayList<Goods>();
        for (Goods exitGoods : goodsService.getGoods()) {
            if (exitGoods.getSellerName().equals(userName)) {
                myGoods.add(exitGoods);
            }
        }
        model.addAttribute("seller", sellerService.getSeller(userName));
        model.addAttribute("myGoods", myGoods);
        return "sellerOnSale";
    }

    @RequestMapping("/deleteGoods")
    public String deleteGoods(@ModelAttribute("seller") Seller seller,
            @RequestParam String goodsId, Model model) {
        goodsService.deleteGoods(goodsId);
        List<Goods> myGoods = new ArrayList<Goods>();
        for (Goods exitGoods : goodsService.getGoods()) {
            if (exitGoods.getSellerName().equals(userName)) {
                myGoods.add(exitGoods);
            }
        }
        model.addAttribute("seller", sellerService.getSeller(userName));
        model.addAttribute("myGoods", myGoods);
        return "sellerOnSale";
    }

}
