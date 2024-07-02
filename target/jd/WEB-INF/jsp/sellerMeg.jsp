<!-- 卖家信息页面 -->
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>用户信息</title>
    <meta http-equiv="cache-control" content="no-cache">
</head>
<body>
    <form:form action="seller/update" method="post" modelAttribute="seller" enctype="multipart/form-data">
        <table>
            <tr>
            <td><label>头像</label></td>
            <td><img src="data:image/jpeg;base64,${picture}" style="width: 200px; height: 200px;" alt="seller.headPortrait"/></td>
			<td><input type="file" name="newImage" /></td>
          	</tr>
            <tr>
                <td><label>用户名:</label></td>
                <td>${seller.name}</td>
            </tr>
            <tr>
                <td><label>密码:</label></td>
                <td><form:input path="pwd" /></td>
            </tr>
            <tr>
            	<td><label>商铺余额</label></td>
               	<td>${seller.money}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="修改信息" /></td>
            </tr>
            <form:hidden value="${seller.name}" path="name"/>
        </table>
    </form:form>
	</body>
