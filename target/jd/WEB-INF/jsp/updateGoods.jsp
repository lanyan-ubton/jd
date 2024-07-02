<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page language="java" import="java.util.*, com.bean.*, com.service.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>商品修改</title>
    <meta http-equiv="cache-control" content="no-cache">
</head>
<body>
    <h1>商品修改</h1>
        <form:form modelAttribute="goods" method="post" action="seller/updateGoods" enctype="multipart/form-data">
            <p>ID: ${goods.id}</p>
            <p>Name: <form:input path="name"  /></p>
            <p>Price: <form:input path="price"  /></p>
            <p>Count: <form:input path="count" /></p>
            <p>Description: <form:textarea path="description" readonly="true" /></p>
            <c:if test="${not empty goods.picture}">
                <p>Picture:</p>
				<img src="data:image/jpeg;base64,${image}" width="100px" height="100px" alt="Goods Picture"/>
            </c:if>
            <td><input type="file" name="newImage" /></td>
            <td><input type="submit" value="提交" /></td>
            <form:input type="hidden" path="id" value="${goods.id}" />
            <form:input type="hidden" path="sellerName" value="${goods.sellerName}" />
        </form:form>
</body>
</html>
