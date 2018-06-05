<%@ page import="henu.bean.BookInfo" %>
<%@ page import="henu.bean.Borrow" %>
<%@ page import="henu.bean.Subscribe" %>
<%@ page import="henu.dao.Pages" %>
<%@ page import="henu.dao.impl.book.Book_Info" %>
<%@ page import="henu.dao.impl.book.book_Borrow" %>
<%@ page import="henu.dao.impl.book.bookSearch" %>
<%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-30
  Time: 15:59
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
<form action="/Servlets/Record_TypeChange" method="post">
    <textarea hidden name="id"><%=request.getParameter("id")%></textarea>
    <input type="submit" value="借阅记录" id="1">
    <input type="submit" value="订购记录" id="2">
    <input type="button" value="返回"
           onclick="javascrtpt:window.location.href='/Functions/bookinfo.jsp'">
    <hr>
    <%
        String pageType = (String) request.getSession().getAttribute("pageType");
        if (pageType == null || (!pageType.equals("RB") && !pageType.equals("RB_"))) {
            request.getSession().setAttribute("pageType", "RB");
            request.getSession().setAttribute("page", null);
        }
        int index_result=(int)request.getSession().getAttribute("index_result");
        System.out.println("test3:"+index_result);
       BookInfo []bookInfos=bookSearch.search(request);
        BookInfo bookInfo = bookInfos[index_result];
        if (bookInfo == null) {
            System.out.println("what");
        }
        pageType = (String) request.getSession().getAttribute("pageType");
        Pages page_now = (Pages) request.getSession().getAttribute("page");
        if (pageType.equals("RB")) {
            page_now=Pages.getpage(request, 4);
            Borrow[] borrows = book_Borrow.getTheRecord(bookInfo.getISBN());
            if(page_now==null)
                System.out.println(page_now);
            int bound = page_now.getFlag() * 8 + 8;
            if (borrows != null && borrows.length > 0) {
    %>
    <%=Book_Info.getBookNamebyISBN(bookInfo.getISBN())%>借阅记录如下：
    <table>
        <thead>
        <tr>
            <th>ISBN</th>
            <th>借阅用户</th>
            <th>借阅日期</th>
            <th>归还日期</th>
            <th>是否超时</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int index = page_now.getFlag() * 8; index < borrows.length && index < bound;
                 index++) {
                System.out.println(index);
        %>
        <tr>
            <td data-label="ISBN"><%=borrows[index].getISBN()%>
            </td>
            <td data-label="借阅用户"><%=borrows[index].getReaderUsername()%>
            </td>

            <td data-label="借阅日期"><%=borrows[index].getBorrowDate()%>
            </td>
            <%
                if (borrows[index].getBackDate() != null) {
            %>
            <td data-label="归还日期"><%=borrows[index].getBackDate()%>
            </td>
            <%} else {%>
            <td data-label="归还日期">未归</td>
            <%}%>
            <td data-label="是否超时"><%=borrows[index].isOverTime()%>
            </td>
        </tr>
        <%
            }%>
        </tbody>
    </table>
    <div></div>
    <div align="center" style="position:relative;top:10%">
        <%
            if (borrows.length > 0)
            //有书
            {
                int flag;
                for (flag = 0; flag < ((borrows.length%8!=0)?(borrows.length /8 + 1):(borrows.length/8)); flag++) {
        %>
        <a name="url" onclick="skip()"><%=flag + 1%>
        </a>
        <%
                }
            }
        %>
    </div>
    <%} else {%>
    无借阅记录
    <%
        }
    } else {
        page_now = Pages.getpage(request, 2);
        Subscribe[] subscribes = book_Borrow.getTheRecordOfSub(bookInfo.getISBN());
        int bound = page_now.getFlag() * 8 + 8;
        if (subscribes != null && subscribes.length > 0) {
    %>
    <table>
        <thead>
        <tr>
            <th>ISBN</th>
            <th>订购日期</th>
            <th>数量</th>
            <th>操作员</th>
            <th>折扣</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int index = page_now.getFlag() * 8; index < subscribes.length && index < bound; index++) {
        %>
        <tr>
            <td data-label="ISBN"><%=subscribes[index].getISBN()%>
            </td>
            <td data-label="订购日期"><%=subscribes[index].getOrderTime()%>
            </td>

            <td data-label="数量"><%=subscribes[index].getNumber()%>
            </td>
            <td data-label="操作员"><%=subscribes[index].getOperator()%>
            </td>
            <td data-label="折扣"><%=subscribes[index].getDiscount()%>
            </td>
        </tr>
        <%
            }%>
        <div align="center" style="position:relative;top:10%">
            <%
                if (subscribes.length > 0)
                //有书
                {
                    int flag;
                    for (flag = 0; flag <((subscribes.length%8!=0)?(subscribes.length /8 + 1):(subscribes.length/8)) ; flag++) {
            %>
            <a name="url" onclick="skip()"><%=flag + 1%>
            </a>
            <%
                    }
                }
            %>
        </div>
        </tbody>
    </table>
    <% } else {
    %>
    无订购记录
    <%
            }
        }
    %>
</form>
</body>
</html>
