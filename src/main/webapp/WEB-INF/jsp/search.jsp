<!-- 搜索页面 -->
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page language="java" import="java.util.*,com.bean.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>搜素结果</title>
    <meta http-equiv="cache-control" content="no-cache">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/public.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/goodsSale.css"/>
        <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js" type="text/javascript" charset="utf-8"></script>
        <script src="${pageContext.request.contextPath}/js/cart.js" type="text/javascript" charset="utf-8"></script>
    <%
		List<Goods> list = (List<Goods>)request.getAttribute("goodsList");
		List<String> images = new ArrayList<String>();
		for(Goods goods:list)
		{
			images.add(Base64.getEncoder().encodeToString(goods.getPicture()));
		}
		int i=0;
	%>
</head>
<body>
        <div class="head">
            <div class="shouye_head">
                <a>搜素结果</a>
            </div>
        </div>
        <div class="cart">
            <div class="cart-head">
                <div class="column g-images">图片</div>
                <div class="column g-goodsname">商品名称</div>
                <div class="column g-props">商品信息</div>
                <div class="column g-price">单价</div>
                <div class="column g-quantity">数量</div>
                <div class="column g-sum">小计</div>
            </div>

            <c:forEach items="${goodsList}" var="goods">
                <div class="carts-goods">
                    <div class="cell c-images">
                    <a >
                        <img src="data:image/jpeg;base64,
    			<%
    				out.print(images.get(i));
    				i++;
    			%>" width="100px" height="100px"  alt="图片"/></a>
                    </div>
                    <div class="cell c-goodsname">${goods.name}</div>
                    <div class="cell c-props">${goods.description}</div>
                    <div class="cell c-price">
                        <span>￥</span>
                        <div class="c-price_num">${goods.price}</div>
                    </div>
                    <div class="cell c-quantity">
						${goods.count}
                    </div>
                    <div class="cell c-sum">
                        <span>￥</span>
                        <div class="c-sum_num">${goods.price}</div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="payment">
            <div class="cart-pay">
                <div class="pay-left">
                </div>
            </div>
        </div>
    </body>
</html>