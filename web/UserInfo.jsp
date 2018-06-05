<%@ page import="henu.bean.Operator" %>
<%@ page import="henu.bean.Reader" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-28
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <SCRIPT LANGUAGE="JavaScript">
        function goBack(){
            parent.location.href = "/Servlets/User_Exit";
        }

    </SCRIPT>
    <title>Title</title>
</head>
<body>
<div >
<%
    Operator operator = (Operator) request.getSession().getAttribute("Operator");
    Reader reader = (Reader) request.getSession().getAttribute("Reader");
    if (operator != null) {
        //操作员界面
%></div>
<p align="center" style="top: 50%;color: black;font-size: large ">当前操作员：<%=operator.getName()%><a
        style="font-size: small" href="javascript:void(0);" onclick ="goBack()">退出登录</a></p>

<% } else {
%>
<p align="center" style="top: 50%;color: black;font-size: large ">当前用户：<%=reader.getName()%><a
        style="font-size: small" href="javascript:void(0);" onclick ="goBack()">退出登录</a></p>
<%
        //reader页面
    }
%>

</div>
</body>
</html>
