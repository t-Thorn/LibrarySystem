<%@ page import="henu.bean.Reader" %>
<%@ page import="henu.bean.Operator" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-29
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        window.onload = function () {
            //给每个书名连接添加索引ID
            var message ='<%=request.getParameter("message")%>';
            if(message!="ok"){
                alert(message);
            }}
    </script>
</head>
<body>
<div align="center">
    <%         request.setCharacterEncoding("UTF-8");
    %>
    <form action="/Servlets/User/User_Pwd"  name="form1" method="post">
        旧密码：<input type="password" name="oldpwd"><br>
        新密码：<input type="password" name="newpwd"><br>
        确认密码：<input type="password" name="confirmpwd"><br>
        <input type="submit" onclick="postusername()">
    </form>
</div>
</body>
</html>
