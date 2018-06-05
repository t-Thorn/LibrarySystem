<%--
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
    <style>
        p{
            align-items: left;
        }
        input
        {
            align-self: right;
        }
    </style>
    <script>
        function setDate() {
            document.getElementById('datePicker').valueAsDate = new Date();
        }
    </script>
</head>
<body onload="setDate()">
<%
    String msg = (String) request.getAttribute("message");
    if (msg!=null){
%>
<script>alert('${message}');</script>
<%}%><div align="center" style="font-size: 14px">
    <form action="/Servlets/User/User_Create" name="form1"  method="post">
        <%
            request.setCharacterEncoding("UTF-8");
        %>
        username:<input type="text"  name="username"><br><br>
        password:<input type="text" name="password"><br><br>
        name:<input type="text" name="name"><br><br>
        gender: <select name="gender" id="method">
        <option value="男">男</option>
        <option value="女">女</option>
          </select><br><br>
        age:<input type="text" name="age"><br><br>
        tel:<input type="text" name="tel"><br><br>
        id:<input type="text" name="id"><br><br>
        birthday:<input style="resize: none;height: 25px" type="date" id="datePicker"
                        name="birthday"/><br><br><br>
        <input type="submit" value="创建">
    </form>
</div>
</body>
</html>
