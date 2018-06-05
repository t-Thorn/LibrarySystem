<%@ page import="henu.bean.Reader" %>
<%@ page import="henu.dao.impl.user.UserInfo" %>
<%@ page import="henu.bean.Borrow" %>
<%@ page import="henu.dao.Pages" %>
<%@ page import="henu.bean.BookInfo" %>
<%@ page import="henu.dao.impl.book.Book_Info" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-31
  Time: 18:34
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
</head>
<body>
<input type="button" value="返回"
       onclick="javascrtpt:window.location.href='/Functions/userview.jsp'">
<hr>
<%
    String pageType=(String) request.getSession().getAttribute("pageType");
    if(pageType==null ||!pageType.equals("RU")){
        request.getSession().setAttribute("pageType","RU");
        request.getSession().setAttribute("page",null);
        request.getSession().setAttribute("method","");
        request.getSession().setAttribute("context","");
    }
    Reader reader= UserInfo.getConcreteReader(request);
    Borrow []borrows=UserInfo.getTheAllBorrowOfUser(reader.getUsername());
    //页面
    Pages page_now=Pages.getpage(request,3);
    int bound = page_now.getFlag() * 8 + 8;
    if (borrows != null &&borrows.length>0) {
%>
借阅记录如下：
<table>
    <thead>
    <tr>
        <th>ISBN</th>
        <th>书名</th>
        <th>借阅日期</th>
        <th>归还日期</th>
        <th>是否超时</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (int index = page_now.getFlag() * 8; index < borrows.length && index < bound; index++) {
    %>
    <tr>
        <td data-label="ISBN"><%=borrows[index].getISBN()%>
        </td>
        <% String bookname=Book_Info.getBookNamebyISBN(borrows[index].getISBN());%>
        <td data-label="书名"><%=bookname%>
        </td>

        <td data-label="借阅日期"><%=borrows[index].getBorrowDate()%>
        </td>
        <%
            if(borrows[index].getBackDate()!=null)
            {
        %>
        <td data-label="归还日期"><%=borrows[index].getBackDate()%>
        </td>
        <%}else{%>
        <td data-label="归还日期">未归</td>
        <%}%>
        <td data-label="是否超时"><%=borrows[index].isOverTime()%>
        </td>
    </tr>
    <%
        }}else{
    %>
    无借阅记录
    <%}%>
    </tbody>
</table>
<div align="center" style="position:relative;top:10%">
    <%
        if (borrows.length > 0)
        //有书
        {
            int flag;
            for (flag = 0; flag <((borrows.length%8!=0)?(borrows.length /8 + 1):(borrows.length/8)); flag++) {
    %>
    <a name="url" onclick="skip()"><%=flag + 1%>
    </a>
    <%
            }
        }
    %>
</div>
</body>
</html>
