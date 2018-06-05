<%@ page import="Servlets.Book_Search" %>
<%@ page import="henu.dao.impl.book.bookSearch" %>
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
    <title>Title</title>
    <script>
        function postISBN() {
            var ISBN=document.getElementById("ISBN");
            form1.action="/Servlets/Book_UpdateInfo?ISBN="+ISBN.innerText;
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
        BookInfo []books=bookSearch.search(request);
        int index=Integer.parseInt(request.getParameter("index"));
        BookInfo target=books[index];
    %>
    <h3>ISBN:<label  id="ISBN"><%=target.getISBN()%></label></h3><br>
    BookName:<br><textarea style="resize: none;height: 25px"
                       name="bookName"><%=target.getBookName()%></textarea><br>
    TypeName:<br><textarea style="resize: none;height: 25px"
                       name="typeName"><%=target.getTypeName()%></textarea><br>
    Writer:  <br><textarea style="resize: none;height: 25px" name="writer"><%=target.getWriter()%></textarea><br>
    translator:<br><textarea style="resize: none;height: 25px"
                         name="translator"><%=target.getTranslator()%></textarea><br>
    publisher:<br><textarea style="resize: none;height: 25px"
                        name="pulisher"><%=target.getPublisher()%></textarea><br>
    publishDate:<br><input style="resize: none;height: 25px" type="date"   id="datePicker" name="pulishDate"/><br>
    price:<br><textarea style="resize: none;height: 25px"name="price"><%=target.getPrice()%></textarea><br>
    remain:<br><textarea style="resize: none;height: 25px" name="remain"><%=target.getRemain()%></textarea><br>
    <br>
    <input type="submit" onclick="postISBN()">
</form>
</div>
</body>
</html>
