package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bean.Buyer;
import com.bean.Goods;
import com.service.BuyerService;
import com.service.GoodsService;
import com.service.SellerService;

@Controller
@RequestMapping("/buyer")
public class BuyerController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SellerService sellerService;
    private static String userName = "";

    // 用户登录
    @RequestMapping("/login")
    public String login(@ModelAttribute("buyer") Buyer buyer, HttpSession session, Model model,
            HttpServletRequest request) {
        if (buyerService.login(buyer)) {
            session.setAttribute("userName", buyer.getName());
            userName = buyer.getName();
            buyer.setHeadPortrait(buyerService.getBuyer(buyer.getName()).getHeadPortrait());
            model.addAttribute("buyer", buyerService.getBuyer(userName));
            if (buyerService.getBuyer(buyer.getName()).getHeadPortrait() != null) {
                session.setAttribute("picture",
                        Base64.getEncoder().encodeToString(buyerService.getBuyer(buyer.getName()).getHeadPortrait()));
            }
            return "main"; // 登录成功跳转的页面
        } else {
            model.addAttribute("error", "用户名或密码有误！");
            model.addAttribute("buyer", buyerService.getBuyer(userName));
            return "buyerLogin";
        }
    }

    // 用户保存
    @RequestMapping("/save")
    public String register(@ModelAttribute("buyer") Buyer buyer, Model model, HttpServletRequest request) {
        if (buyerService.save(buyer)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", buyer.getName());
            model.addAttribute("buyer", buyer);
            return "buyerLogin"; // 注册成功跳转的页面
        } else {
            model.addAttribute("error", "用户名或密码有误！");
            model.addAttribute("buyer", buyer);
            return "buyerReg";
        }
    }

    // 搜素检查
    @RequestMapping("/searchCheck")
    public String searchCheck(@RequestParam("name") String name, Model model) {
        if (name == null || name.trim().isEmpty()) {
            // 如果查询名为空，直接返回，避免无效查询
            return "main";
        }
        // 使用模糊搜索
        List<Goods> goodsList = goodsService.getGoods();
        List<Goods> goodsList2 = new ArrayList<>();
        String searchPattern = ".*" + Pattern.quote(name) + ".*";
        Pattern pattern = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
        for (Goods data : goodsList) {
            if (pattern.matcher(data.getName()).find()) {
                goodsList2.add(data);
            }
        }
        model.addAttribute("goodsList", goodsList2); // 将搜索结果传递给视图
        return "search";
    }

    // 用户信息更新
    @RequestMapping("/update")
    public String updateBuyerMsg(@ModelAttribute("buyer") Buyer buyer,
            @RequestParam MultipartFile newImage, Model model) {
        if (!newImage.isEmpty()) {
            try {
                buyer.setHeadPortrait(newImage.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            buyer.setHeadPortrait(buyerService.getBuyer(userName).getHeadPortrait());
        }
        // 数据库更新
        buyerService.update(buyer);
        buyer = buyerService.getBuyer(userName);
        System.out.println("buyer:" + buyerService.getBuyer(userName).getName()
                + buyerService.getBuyer(userName).getHeadPortrait());
        if (buyerService.getBuyer(userName).getHeadPortrait() != null)
            model.addAttribute("picture",
                    Base64.getEncoder().encodeToString(buyerService.getBuyer(userName).getHeadPortrait()));
        // 返回视图名称
        return "buyerMeg";
    }

    // 添加商品到购物车
    @RequestMapping("/addShoppingCart")
    public String addShoppingCart(Model model, @ModelAttribute("id") String id, HttpSession session) {
        Goods goods = goodsService.getGoods(id);
        Buyer buyer = buyerService.getBuyer((String) session.getAttribute("userName"));
        if (buyerService.addGoods(buyer, goods))
            return "redirect:shoppingCart";
        else
            return "main";
    }

    // 购物车
    @RequestMapping("/shoppingCart")
    public String goShoppingCart(Model model, HttpSession session) {
        Buyer buyer = buyerService.getBuyer((String) session.getAttribute("userName"));
        List<Goods> list = buyerService.getGoods(buyer);
        model.addAttribute("goods", list);
        return "shoppingCart";
    }

    // 从购物车删除物品
    @RequestMapping("/deleteGoods")
    public String deleteGoods(@ModelAttribute("id") String id, HttpSession session, Model model) {
        Goods goods = goodsService.getGoods(id);
        Buyer buyer = buyerService.getBuyer((String) session.getAttribute("userName"));
        if (buyerService.deleteGoods(buyer, goods))
            return "redirect:shoppingCart";
        else
            return "main";
    }

    // 购买商品
    @RequestMapping("/sale")
    public String sale(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getSession().getAttribute("userName");
        Buyer buyer = buyerService.getBuyer(username);
        int money = Integer.parseInt(request.getParameter("total"));
        buyerService.sale(buyer, money);
        sellerService.updateSeller(username, money);
        try {
            response.getWriter().println("购买成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "main";
    }
}