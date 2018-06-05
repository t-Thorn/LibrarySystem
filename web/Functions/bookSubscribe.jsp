<%@ page import="henu.dao.impl.book.Book_Info" %>
<%@ page import="henu.bean.BookInfo" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-29
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图书订购</title>
    <script>
        window.onsubmit= function jump() {
            var ISBN=document.getElementById("ISBN");
            form1.action="/Servlets/subrcibe_Book?ISBN="+ISBN.innerText;
            form1.submit();
        }
    </script>
</head>
<body>
<div align="center">
    <form action="/Servlets/subrcibe_Book" name="form1" method="post">
        <%
            int ISBN=Integer.parseInt(request.getParameter("ISBN"));
            String bookname=Book_Info.getBookNamebyISBN(ISBN);
        %>
        <h3>ISBN:<label  id="ISBN"><%=ISBN%></label></h3><br>
        BookName:
        <%=bookname%><br>
        Number:<br>
        <textarea style="resize: none;height: 25px"
                  name="number"></textarea><br>
        Discount:<br>
        <textarea style="resize: none;height: 25px"
                  name="discount"></textarea><br>
        <input type="submit" >
    </form>
</div>
</body>
</html>
