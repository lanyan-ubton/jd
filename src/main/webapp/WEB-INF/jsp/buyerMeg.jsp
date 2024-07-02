<!-- 买家信息页面 -->
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <form:form action="buyer/update" method="post" modelAttribute="buyer" enctype="multipart/form-data">
        <table>
            <tr>
            <td><label>头像</label></td>
            <td><img src="data:image/jpeg;base64,${picture}" style="width: 200px; height: 200px;" alt="picture"/></td>
			<td><input type="file" name="newImage" /></td>
          	</tr>
            <tr>
                <td><label>用户名:</label></td>
                <td>${buyer.name}</td>
            </tr>
            <tr>
                <td><label>密码:</label></td>
                <td><form:input path="pwd" /></td>
            </tr>
             <tr>
                <td><label>性别:</label></td>
				<td>
					<label>男</label>
					<form:radiobutton value="男" path="sex" />
					<label>女</label>
					<form:radiobutton value="女" path="sex"/>
            	</td>
            </tr>
            <tr>
            	<td><label>余额</label></td>
               	<td>${buyer.money}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="修改信息" /></td>
            </tr>
            <form:hidden value="${buyer.name }" path="name"/>
        </table>
    </form:form>
	</body>
</html>