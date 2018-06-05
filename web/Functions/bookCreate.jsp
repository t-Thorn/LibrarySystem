<%@ page import="henu.bean.BookInfo" %>
<%@ page import="henu.dao.impl.book.Book_Info" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-29
  Time: 13:30
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
        function postISBN() {
            var ISBN=document.getElementById("ISBN");
            form1.action="/Servlets/Book_Create?ISBN="+ISBN.innerText;
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
            int ISBN=Book_Info.getISBN();
        %>
        <h3>ISBN:<label  id="ISBN"><%=ISBN%></label></h3><br>
        BookName:<br><textarea style="resize: none;height: 25px"
                               name="bookName"></textarea><br>
        TypeName:<br><textarea style="resize: none;height: 25px"
                               name="typeName"></textarea><br>
        Writer:  <br><textarea style="resize: none;height: 25px" name="writer"></textarea><br>
        translator:<br><textarea style="resize: none;height: 25px"
                                 name="translator"></textarea><br>
        publisher:<br><textarea style="resize: none;height: 25px"
                                name="pulisher"></textarea><br>
        publishDate:<br><input style="resize: none;height: 25px" type="date" id="datePicker" name="pulishDate"/><br>
        price:<br><textarea style="resize: none;height: 25px"name="price"></textarea><br>
        remain:<br><textarea style="resize: none;height: 25px" name="remain"></textarea><br>
        <br>
        <input type="submit" onclick="postISBN()">
    </form>
</div>
</body>
</html>
