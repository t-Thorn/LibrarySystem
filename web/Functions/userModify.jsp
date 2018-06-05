<%@ page import="henu.bean.Reader" %>
<%@ page import="henu.dao.impl.book.bookSearch" %><%--
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
        function postusername() {
            var username=document.getElementById("username");
            form1.action="/Servlets/User/User_Update?username="+username.innerText;
            form1.submit();
        }
        function setDate(){
            document.getElementById('datePicker').valueAsDate = new Date();
        }
    </script>
</head>
<body onload="setDate()">
<div align="center">
    <form action="#"  name="form1" method="post">
        <%
            request.setCharacterEncoding("UTF-8");
            Reader[]readers= bookSearch.searchUser(request);
            int index=Integer.parseInt(request.getParameter("index"));
            Reader target=readers[index];
        %>
        <h3>用户名:<label  id="username"><%=target.getUsername()%></label></h3><br>
        密码:<br><textarea style="resize: none;height: 25px"
                               name="password"><%=target.getPassword()%></textarea><br>
        名字:<br><textarea style="resize: none;height: 25px"
                               name="name"><%=target.getName()%></textarea><br>
        性别:  <br><textarea style="resize: none;height: 25px" name="gender"><%=target.getGender()%></textarea><br>
        年龄:<br><textarea style="resize: none;height: 25px"
                                 name="age"><%=target.getAge()%></textarea><br>
        联系方式:<br><textarea style="resize: none;height: 25px"
                                name="tel"><%=target.getTel()%></textarea><br>
        生日:<br><input style="resize: none;height: 25px" type="date"  id="datePicker"
                               name="birthday"/><br>
        身份证:<br><textarea style="resize: none;height: 25px"name="id"><%=target.getID()%></textarea><br>
        余额:<br><textarea style="resize: none;height: 25px" name="money"><%=target.getMoney()%></textarea><br>
        <br>
        <input type="submit" onclick="postusername()">
    </form>
</div>
</body>
</html>
