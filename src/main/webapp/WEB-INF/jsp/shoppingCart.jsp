<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*,com.bean.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %><html>
    <head>
        <meta charset="UTF-8">
        <title>商城购物车界面</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/public.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/carts.css"/>
        <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js" type="text/javascript" charset="utf-8"></script>
        <script src="${pageContext.request.contextPath}/js/cart.js" type="text/javascript" charset="utf-8"></script>
    <%
		List<Goods> list = (List<Goods>)request.getAttribute("goods");
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
                <a>购物车</a>
            </div>
        </div>
        <div class="cart">
            <div class="cart-head">
                <div class="column g-select">
                    <div class="g-checkbox">
                        <input type="checkbox" class="all each">
                    </div>
                    全选
                </div>
                <div class="column g-images">图片</div>
                <div class="column g-goodsname">商品名称</div>
                <div class="column g-props">商品信息</div>
                <div class="column g-price">单价</div>
                <div class="column g-quantity">数量</div>
                <div class="column g-sum">小计</div>
                <div class="column g-action">操作</div>
            </div>

            <c:forEach items="${goods}" var="goods">
                <div class="carts-goods">
                    <div class="cell c-select">
                        <div class="c-checkbox">
                            <input type="checkbox" class="each">
                        </div>
                    </div>
                    <div class="cell c-images">
                        <img src="data:image/jpeg;base64,
    			<%
    				out.print(images.get(i));
    				i++;
    			%>" width="100px" height="100px"  alt="图片"/>
                    </div>
                    <div class="cell c-goodsname">${goods.name}</div>
                    <div class="cell c-props">${goods.name}</div>
                    <div class="cell c-price">
                        <span>￥</span>
                        <div class="c-price_num">${goods.price}</div>
                    </div>
                    <div class="cell c-quantity">
                        <button type="button"  class="reduce">-</button>
                        <input type="text" value="1" class="text_num">
                        <button type="button" class="add">+</button>
                    </div>
                    <div class="cell c-sum">
                        <span>￥</span>
                        <div class="c-sum_num">${goods.price}</div>
                    </div>
                    <div class="cell c-action">
                        <a href="${pageContext.request.contextPath}/buyer/deleteGoods?id=${goods.id}" class="remove">移除商品</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="payment">
            <div class="cart-pay">
                <div class="pay-left">
                    <div class="select_all">
                        <input type="checkbox" class="all each">
                        全选
                    </div>
                    <div class="goods_num">
                        已选择数量:
                        <span>0</span>
                        件
                    </div>
                </div>
                <div class="pay-right">
                    <div class="right_jie">
                        <div class="btn">
                            <a href="">结算</a>
                        </div>
                        <div class="price-sum">
                            <div class="price-show">总价是：</div>
                            <div class="show-money">
                                ￥
                                <span>00.00</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
