<%@ page import="henu.bean.Borrow" %>
<%@ page import="henu.bean.Reader" %>
<%@ page import="henu.dao.impl.book.Book_Info" %>
<%@ page import="henu.dao.impl.book.book_Borrow" %>
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
    <style type="text/css">
        a {
            font-size: 16px
        }

        a:link {
            color: blue;
            text-decoration: none;
        }

        a:active {
            color: red;
        }

        a:visited {
            color: purple;
            text-decoration: none;
        }

        a:hover {
            color: red;
            text-decoration: underline;
        }

        body {
            font-family: arial;
        }

        table {
            border: 1px solid #ccc;
            width: 80%;
            margin: 0;
            padding: 0;
            border-collapse: collapse;
            border-spacing: 0;
            margin: 0 auto;
        }

        table tr {
            border: 1px solid #ddd;
            padding: 5px;
        }

        table th, table td {
            padding: 10px;
            text-align: center;
        }

        table th {
            text-transform: uppercase;
            font-size: 14px;
            letter-spacing: 1px;
        }

        @media screen and (max-width: 600px) {

            table {
                border: 0;
            }

            table thead {
                display: none;
            }

            table tr {
                margin-bottom: 10px;
                display: block;
                border-bottom: 2px solid #ddd;
            }

            table td {
                display: block;
                text-align: right;
                font-size: 13px;
                border-bottom: 1px dotted #ccc;
            }

            table td:last-child {
                border-bottom: 0;
            }

            table td:before {
                content: attr(data-label);
                float: left;
                text-transform: uppercase;
                font-weight: bold;
            }
        }

        .note {
            max-width: 80%;
            margin: 0 auto;
        }
    </style>
    <script type="text/javascript">
        window.onload=function(){
            //给每个书名连接添加索引ID
            var errori ='<%=request.getParameter("error")%>';
            if(errori=='yes'){
                alert("还书失败!");
            }
            var books=document.getElementsByTagName("input");
            for(var i=0;i<books.length;i++)
            {
                books[i].setAttribute('id',i);
            }
            var url=document.getElementsByName("url");
            for(var i=0;i<url.length;i++)
            {
                url[i].setAttribute('id',i);
            }
        }
        function back(event)
        {
            event = event ? event : window.event;
            var books = event.srcElement ? event.srcElement : event.target;
            //根据源事件添加
            jumps.action="/Servlets/Book_Return?id="+books.id;
            jumps.submit();
        }
        //取出传回来的参数error并与yes比较

    </script>
</head>
<body style="background:transparent;">

<form action="#" method="post"  name="jumps">
    <%
        Reader reader=(Reader) request.getSession().getAttribute("Reader");
        float []margin=book_Borrow.updateBorrowByReader(reader);//更新信息
        Borrow[] borrow = book_Borrow.getTheBorrowedBook(reader);
        if (borrow.length > 0 )
        //有书
        {
    %>
    你当前代还的书籍如下：<br><br><br>
<table>
    <thead>
    <tr>
        <th>ISBN</th>
        <th>书名</th>
        <th>当前状态</th>
        <th>借书日期</th>
        <th>是否超时</th>
        <th>罚款金额</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <%
            for (int index = 0; index < borrow.length; index++) {
    %>
    <tr>
        <td data-label="ISBN"><%=borrow[index].getISBN()%>
        </td>
        <td data-label="书名"><%=Book_Info.getBookNamebyISBN(borrow[index].getISBN())%>
        </td>
        <td data-label="当前状态">未还</td>
        <td data-label="借书日期"><%=borrow[index].getBorrowDate()%>
        <td data-label="是否超时"><%=(borrow[index].isOverTime()?"是":"否")%>
        <td data-label="罚款金额"><%=margin[index]%>
        <td data-label="操作"><input value="归还" type="submit" onclick="back()">
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
} else {
%>
你当前没有代还书籍
<%
    }
%>

</form>
</body>
</html>
