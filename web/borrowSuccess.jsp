<%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-30
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
借阅成功3秒后自动跳转到浏览页面<% response.setHeader("refresh","3;url=/Functions/bookinfo.jsp");%>
</body>
</html>
