<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>用户注册页面</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/public.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
        <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js" type="text/javascript" charset="utf-8"></script>
        <script src="${pageContext.request.contextPath}/js/jquery.validate.min.js" type="text/javascript" charset="utf-8"></script>
        <script src="${pageContext.request.contextPath}/js/login.js" type="text/javascript" charset="utf-8"></script>

    </head>
    <body>
        <div class="head">
            <div class="shouye_head">
                <a href="${pageContext.request.contextPath}/view/main">商城首页</a>
            </div>
        </div>
        <div class="dowebok" id="dowebok">
            <!-- 注册 -->
            <div class="form-container sign-up">
               <form:form modelAttribute="buyer" action="${pageContext.request.contextPath}/buyer/save" method="post" class="register">
               		<h1>注册</h1>
               		<form:input path="name" id="username" name="name" placeholder="请输入用户名" />
               		<form:password path="pwd" id="pwd" name="pwd" placeholder="请输入密码"/>
               		<input type="password" id="second_pwd" name="second_pwd" placeholder="请再次输入密码"/>
                    <button>注册</button>
    			</form:form>
            </div>
            <!-- 登录 -->
            <div class="form-container sign-in">
                <form:form modelAttribute="buyer" action="${pageContext.request.contextPath}/buyer/login" method="post" class="sign">
                	<h1>登录</h1>
                    <form:input path="name" name="name" placeholder="账号"/>
                    <form:password path="pwd" name="pwd" placeholder="密码"/>
                    <button>登录</button>
        		</form:form>
            </div>
            <!-- 两边的蒙版 -->
            <div class="overlay-container">
                <div class="overlay">
                    <!-- 登录蒙版 -->
                    <div class="overlay-panel mask-left">
                        <h1>已有帐号？</h1>
                        <p>请使用您的帐号进行登录</p>
                        <button class="ghost" id="signIn">登录</button>
                    </div>
                    <!-- 注册蒙版 -->
                    <div class="overlay-panel mask-right">
                        <h1>没有帐号？</h1>
                        <p>立即注册加入我们，和我们一起开始旅程吧</p>
                        <button class="ghost" id="signUp">注册</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>