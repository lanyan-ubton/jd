<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
    	<base href="<%=basePath%>">
   	 	<title>添加商品</title>
   	 	<meta http-equiv="cache-control" content="no-cache">
	</head>
   	<body>
   		<form:form action="seller/addGoods" method="post" modelAttribute="goods" enctype="multipart/form-data">
    	<table>
      	  <tr>
       	    <td><label>商品名:</label></td>
            <td><form:input path="name" /></td>
       	  </tr>
          <tr>
            <td><label>价格:</label></td>
            <td><form:input path="price" /></td>
          </tr>
          <tr>
            <td><label>数量:</label></td>
            <td><form:input path="count" /></td>
          </tr>
          <tr>
            <td><label>描述:</label></td>
            <td><form:input path="description" /></td>
          </tr>
          <tr>
            <td><label>图片:</label></td>
            <td><input type="file" name="image" /></td>
          </tr>
          <tr>
            <td colspan="2"><input type="submit" value="添加" /></td>
          </tr>
        </table>
		</form:form>
	</body>
</html>
