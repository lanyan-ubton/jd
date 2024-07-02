package com.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    public String userName;

    @ModelAttribute
    public void getLoginName(HttpSession session) {
        userName = (String) session.getAttribute("userName");
        if (userName == null)
            userName = "";
    }

}
