<!-- 商品展示页面 -->
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page language="java" import="java.util.*,com.bean.*,com.service.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品展示</title>
    <meta http-equiv="cache-control" content="no-cache">
    <link rel="stylesheet" type="text/css" href="css/public.css"/>
    <link rel="stylesheet" type="text/css" href="css/book.css"/>
</head>
<body>
    <div class="head">
        <div class="shouye_head">
            <div class="shou_head">
                <a href="#">商城首页</a>
            </div>
            <div class="car">
                <a href="buyer/shoppingCart"><img src="image/购物车.png" height="28px">
                    <span>购物车</span>
                </a>
            </div>
        </div>
    </div>
    <div class="search_kuang">
        <div class="search">
            <input type="text" class="text">
            <button><img src="image/搜索图标.png" width="40px"></button>
        </div>
    </div>
    <div class="xi_nav"></div>
    <div class="w">
        <div class="preview" id="preview">
            <div class="photo_xi">
                <img src="data:image/jpeg;base64,${picture}" width="360px" height="360px"  alt="图片"/>
            </div>
        </div>
        <div class="itemInfo-wrap">
            <div class="x-goodsname">
                <h3>${goods.name}</h3>
            </div>
            <div class="news">
                <div class="" title="${goods.description}">
                    ${goods.description}
                </div>
            </div>
            <div class="x-price">
                <span>￥</span>
                <div class="x-price_num">${goods.price}</div>
            </div>
            <div class="x-action">
                <div class="liji">
                    <a href="#">立即购买</a>
                </div>
                <div class="add_cart">
                    <a href="buyer/addShoppingCart?id=${goods.id} ">加入购物车</a>
                </div>
            </div>
        </div>

    </div>
    </body>
</html>