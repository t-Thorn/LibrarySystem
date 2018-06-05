<%@ page import="javafx.scene.control.Alert" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-26
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" language="javascript">
        function resizeFrame() {
            var vH = document.getElementById("middle").offsetHeight;
            var y = (parseInt(document.documentElement.clientHeight) - vH) / 2;
            var vW = document.getElementById("middle").offsetWidth;
            var x = (parseInt(document.documentElement.clientWidth) - vW) / 2;
            document.getElementById("middle").setAttribute("style", "margin-top:" + y + "px;margin-left:" + x + "px;");
        }

        window.onresize = resizeFrame;
        window.onload = resizeFrame;

    </script>
    <script type="text/javascript" language="javascript">
        function validate() {
            for (var i = 0; i < document.login.elements.length - 1; i++) {
                if (document.login.elements[i].value == "") {
                    alert("当前表单不能有空项");
                    document.login.elements[i].focus();
                    return false;
                }
            }
            return true;
        }
    </script>
    <title>欢迎登陆Thorn的图书管理系统</title>
</head>
<body background="/image/background.jpg">
<h1></h1>

<h1 align="center" style="top: 10%">图书管理系统111</h1>
<div id="middle" style="position:absolute;">

    <form action="/Servlets/User_Servlets" method="post" name="login" onSubmit="return validate()">
        <p style="font-family:Consolas;font-size: 18px">请输入用户名：</p>
        <input type="text" name="username"><br><br>
        <p style="font-family:Consolas;font-size: 18px">请输入用户密码：</p>
        <input type="password" name="password">
        <br>
        <br>
        <div style="position:absolute;left: 48%">
            <input type="submit" value="登录" style="height: 40px;width: 60px">
        </div>
    </form>
    <div style="position:absolute;left: 48%;top: 70%">
        <%
            String msg = (String) request.getAttribute("error");
            if (msg != null){
        %>
        <script>alert('${error}');</script>
        <%}%>
    </div>
</div>
</body>
</html>
