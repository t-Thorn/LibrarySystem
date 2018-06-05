<%@ page import="henu.bean.Operator" %>
<%@ page import="henu.bean.Reader" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-28
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        .sub_menue {
            display: none; /*先将子菜单设置为隐藏*/
        }

        li:hover .sub_menue {
            display: block; /*设置鼠标滑过动作，以块级元素的形式显示出子菜单*/
        }
    </style>
    <script>
        function jump(z) {
            jumps.action="/Servlets/context_transfer?function="+z;
            jumps.submit();
        }
    </script>
</head>
<body>
<form action="#" method="post" target="tt" name="jumps">
    <input type="hidden" id="indate" name="indate"/>
<div align="left" style="top:0px;width: 20%;height: 100px;color: black">
    <ul class="menue" ID="Fuction">
        <li>图书管理
            <ul class="sub_menue">
                <li onclick="jump('/Functions/bookinfo.jsp')">图书查询</li>
                <%
                    Operator operator = (Operator) request.getSession().getAttribute("Operator");
                    if (operator != null) {
                        //操作员界面
                %>
                <li onclick="jump('/Functions/bookCreate.jsp')">图书创建</li>
                <% } else {
                %>
                <li onclick="jump('/Functions/bookReturn.jsp')">图书归还</li>
                <% }
                %>
            </ul>
        </li>
        <li>用户管理
            <ul class="sub_menue">
                <%
                    if (operator != null) {
                        //操作员界面
                %>
                <li onclick="jump('/Functions/userCreate.jsp')">用户创建</li>
                <li onclick="jump('/Functions/userview.jsp?message=ok')">用户信息浏览</li>
                <% }
                %>
                <li onclick="jump('/Functions/pwdModify.jsp?message=ok')">密码修改</li>
            </ul>
        </li>
    </ul>
</div>
</form>
<%
    if (operator != null) {
        //操作员界面

%>
<iframe scrolling="no" frameborder="0" style="position:absolute;left:15%;top: 0;
    width: 80%;height:90%" scrolling="auto" allowtransparency="true"
        name="tt"
        src="Functions/bookinfo.jsp">
</iframe>
<% }
else {
%>
<iframe scrolling="no" frameborder="0" style="position:absolute;left:15%;top: 0;
    width: 80%;height:90%" scrolling="auto" name="tt" allowtransparency="true" src="Functions/bookReturn.jsp">
</iframe>
<%}
%>
</body>
</html>
