<%@ page import="henu.bean.BookInfo" %>
<%@ page import="henu.bean.Borrow" %>
<%@ page import="henu.bean.Operator" %>
<%@ page import="henu.bean.Reader" %>
<%@ page import="henu.dao.Pages" %>
<%@ page import="henu.dao.impl.book.bookSearch" %>
<%@ page import="henu.dao.impl.book.book_Borrow" %>
<%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-29
  Time: 13:19
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
    <script>
        window.onload = function () {
            //给每个书名连接添加索引ID
            var errori = '<%=request.getParameter("error")%>';
            if (errori == 'yes') {
                alert("操作失败!");
            }
            var borrow = document.getElementsByName("borrow");
            for (var i = 0; i < borrow.length; i++) {
                borrow[i].setAttribute('id', i);
            }
            var delete_ = document.getElementsByName("delete");
            for (var i = 0; i < delete_.length; i++) {
                delete_[i].setAttribute('id', i);
            }
            var subscribe = document.getElementsByName("subscribe");
            for (var i = 0; i < subscribe.length; i++) {
                subscribe[i].setAttribute('id', i);
            }
            var url = document.getElementsByName("url");
            for (var i = 0; i < url.length; i++) {
                url[i].setAttribute('id', i);
            }
            var modify = document.getElementsByName("bookmodify");
            for (var i = 0; i < modify.length; i++) {
                modify[i].setAttribute('id', i);
            }
            var trecord = document.getElementsByName("record");
            for (var i = 0; i < trecord.length; i++) {
                trecord[i].setAttribute('id', i);
            }

        }

        function jumptoborrow(event) {
            event = event ? event : window.event;
            var borrow = event.srcElement ? event.srcElement : event.target;

            jumps.action = "/Servlets/bookBorrw?id=" + borrow.id;
            jumps.submit();
        }

        function skip(event) {
            //翻页
            event = event ? event : window.event;
            var urls = event.srcElement ? event.srcElement : event.target;
            jumps.action = "/Servlets/page_Jump?id=" + urls.id;
            jumps.submit();
        }

        function modify(event) {
            //翻页

            event = event ? event : window.event;
            var update = event.srcElement ? event.srcElement : event.target;
            jumps.action = "/Servlets/Book_Update?id=" + update.id;
            jumps.submit();
        }

        function book_delete(event) {
            //翻页

            event = event ? event : window.event;
            var book = event.srcElement ? event.srcElement : event.target;

            jumps.action = "/Servlets/Book_Delete?id=" + book.id;
            jumps.submit();
        }

        function book_subscribe(event) {
            //翻页

            event = event ? event : window.event;
            var book = event.srcElement ? event.srcElement : event.target;
            jumps.action = "/Servlets/Book_Subscribe?id=" + book.id;
            jumps.submit();
        }

        function book_RB(event) {
            //翻页

            event = event ? event : window.event;
            var book = event.srcElement ? event.srcElement : event.target;
            jumps.action = "/Servlets/Book_Record?id=" + book.id;
            jumps.submit();
        }
    </script>
</head>
<body style="background:transparent;">

<form action="/Servlets/Book_Search" method="post" name="jumps">
    检索图书：<br>
    <select name="method" id="method">
        <option value="ISBN">ISBN</option>
        <option value="name">书名</option>
        <option value="type">类型</option>
        <option value="writer">作者</option>
    </select>
    <input type="text" id="con" name="context">
    <input type="submit" value="检索">
    <hr>
    <%
        //初始化
        String pageType = (String) request.getSession().getAttribute("pageType");
        if (pageType == null || !pageType.equals("book")) {
            request.getSession().setAttribute("pageType", "book");
            request.getSession().setAttribute("page", null);
            request.getSession().setAttribute("method", "");
            request.getSession().setAttribute("context", "");
        }
        request.setCharacterEncoding("UTF-8");
        Reader reader = (Reader) request.getSession().getAttribute("Reader");
        Operator operator = (Operator) request.getSession().getAttribute("Operator");
        BookInfo[] books = bookSearch.search(request);
        //页面

        Pages page_now = Pages.getpage(request, 1);
        int tt = page_now.getFlag() * 8;
        if (books.length <= tt && books.length > 0) {
            page_now.setFlag(0);

            request.getSession().setAttribute("page", page_now);
        }
        int bound = page_now.getFlag() * 8 + 8;
        if (books != null && books.length > 0) {
    %>
    <table>
        <thead>
        <tr>
            <th>ISBN</th>
            <th>书名</th>
            <th>类型</th>
            <th>作者</th>
            <th>出版社</th>
            <th>出版日期</th>
            <th>价格</th>
            <th>余量</th>
            <% if (reader != null) {%>
            <th>是否可借</th>
            <%} else {%>
            <th>订阅</th>
            <th>删除</th>
            <th>借阅记录</th>
            <%}%>
        </tr>
        </thead>
        <tbody>
        <%
            for (int index = page_now.getFlag() * 8; index < books.length && index < bound; index++) {
        %>
        <tr>
            <td data-label="ISBN"><%=books[index].getISBN()%>
            </td>
            <td data-label="书名"><% if (operator != null) {%><a name="bookmodify"
                                                               onclick="modify()"><%=books[index].getBookName()%>
            </a><%} else {%>
                <%=books[index].getBookName()%><%}%>
            </td>
            <td data-label="类型"><%=books[index].getTypeName()%>
            </td>
            <td data-label="作者"><%=books[index].getWriter()%>
            </td>
            <td data-label="出版社"><%=books[index].getPublisher()%>
            </td>
            <td data-label="出版日期"><%=books[index].getPublishDate()%>
            </td>
            <td data-label="价格"><%=books[index].getPrice()%>
            </td>
            <td data-label="余量"><%=books[index].getRemain()%>
            </td>
            <% if (reader != null) {%>
            <td data-label="是否可借">
                    <% if (books[index].getRemain() != 0) {

                    //先更新用户的借书信息
                    book_Borrow.updateBorrowByReader(reader);
                    Borrow[] borrow = book_Borrow.getTheBorrowedBook(reader);
                    int number = 0;//OVERtime数量
                    for (Borrow b : borrow) {
                        if (b.isOverTime())
                            number++;
                    }
                    if (borrow.length >= 5 || number > 0) {

                %>
                不可借
                    <%
                    }
                 else {
                %>
                <input value="借阅" type="submit" name="borrow" onclick="jumptoborrow()">
                    <%
                    }}
                %>
                    <%}else{%>
            <td data-label="订购"><input value="订购" type="submit"
                                       name="subscribe" onclick="book_subscribe()"></td>
            <td data-label="删除">
                <input value="删除" type="submit" name="delete" onclick="book_delete()"></td>
            <td data-label="借阅记录"><input value="借阅记录" name="record" type="submit"
                                         onclick="book_RB()"></td>
            <%}%>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%} else {%>
    没有相关图书
    <%
        }
    %>
    <div align="center" style="position:relative;top:10%">
        <%
            if (books.length > 0)
            //有书
            {
                int flag;

                for (flag = 0; flag < ((books.length % 8 != 0) ? (books.length / 8 + 1) : (books.length / 8));
                     flag++) {
        %>
        <a name="url" onclick="skip()"><%=flag + 1%>
        </a>
        <%
                }
            }
        %>
    </div>
</form>

</body>
</html>
