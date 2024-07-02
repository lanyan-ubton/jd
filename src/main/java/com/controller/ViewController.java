package com.controller;

import java.util.Base64;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//掌管视图跳转的神
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bean.Buyer;
import com.bean.Goods;
import com.bean.Seller;
import com.service.BuyerService;
import com.service.GoodsService;
import com.service.SellerService;

@Controller
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping("/sellerLogin")
    public String goSellerLogin(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellerLogin";
    }

    @RequestMapping("/sellerReg")
    public String goSellerReg(Model model) {
        model.addAttribute("seller", new Seller());
        return "sellerReg";
    }

    @RequestMapping("/sellerMsg")
    public String goSellerMsg(@RequestParam String sellerName, Model model) {
        model.addAttribute("seller", sellerService.getSeller(sellerName));
        return "sellerMsg";
    }

    @RequestMapping("/buyerLogin")
    public String goBuyerLogin(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "buyerLogin";
    }

    @RequestMapping("/buyerReg")
    public String goBuyerReg(Model model) {
        model.addAttribute("buyer", new Buyer());
        return "buyerReg";
    }

    @RequestMapping("/buyerMsg")
    public String goBuyerMsg(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("buyer", buyerService.getBuyer(userName));
        return "buyerMsg";
    }

    @RequestMapping("/addGoods")
    public String addGoods(Model model) {
        model.addAttribute("goods", new Goods());
        return "addGoods";
    }

    @RequestMapping("/updateGoods")
    public String updateGoods(@RequestParam String goodsId, Model model) {
        Goods goods = goodsService.getGoods(goodsId);
        byte[] imageData = goods.getPicture();
        // 将 byte[] 转换为 Base64 字符串
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        // 将 Base64 字符串添加到模型中
        model.addAttribute("image", base64Image);
        model.addAttribute("goods", goodsService.getGoods(goodsId));
        return "updateGoods";
    }

    @RequestMapping("/search")
    public String search(Model model) {
        model.addAttribute("goods", new Goods());
        return "search";
    }

    @RequestMapping("/buyerMeg")
    public String buyerMeg(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("buyer", buyerService.getBuyer(userName));
        return "buyerMeg";
    }

    @RequestMapping("/sellerMeg")
    public String sellerMeg(Model model, HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("seller", sellerService.getSeller(userName));
        return "sellerMeg";
    }

    @RequestMapping("/goodsShow")
    public String goodsShow(Model model, @RequestParam(value = "id") String id) {
        if (id == null || goodsService.getGoods(id) == null) {
            return "main";
        }
        Goods goods = goodsService.getGoods(id);
        model.addAttribute("goods", goods);
        model.addAttribute("picture", Base64.getEncoder().encodeToString(goods.getPicture()));
        return "goodsShow";
    }
}
