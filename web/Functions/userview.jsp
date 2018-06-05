<%@ page import="henu.bean.Operator" %>
<%@ page import="henu.dao.Pages" %>
<%@ page import="henu.bean.Reader" %>
<%@ page import="henu.dao.impl.book.bookSearch" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-30
  Time: 15:57
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
            var errori ='<%=request.getParameter("error")%>';
            if(errori=='yes'){
                alert("操作失败!");
            }
            var delete_ = document.getElementsByName("delete");
            for (var i = 0; i < delete_.length; i++) {
                delete_[i].setAttribute('id', i);
            }
            var url = document.getElementsByName("url");
            for (var i = 0; i < url.length; i++) {
                url[i].setAttribute('id', i);
            }
            var modify = document.getElementsByName("usermodify");
            for (var i = 0; i < modify.length; i++) {
                modify[i].setAttribute('id', i);
            }

            var record = document.getElementsByName("record");
            for (var i = 0; i < record.length; i++) {
                record[i].setAttribute('id', i);
            }

        }

        function skip(event) {
            //翻页
            event = event ? event : window.event;
            var urls = event.srcElement ? event.srcElement : event.target;
            jumps.action = "/Servlets/page_Jump?id=" + urls.id;
            jumps.submit();
        }
        function modify(event) {

            event = event ? event : window.event;
            var update = event.srcElement ? event.srcElement : event.target;
            alert(update.id)
            jumps.action = "/Servlets/User/User_Modify?id=" + update.id;
            jumps.submit();
        }
        function user_delete(event) {
            //翻页

            event = event ? event : window.event;
            var book = event.srcElement ? event.srcElement : event.target;
            alert(book.id)
            jumps.action = "/Servlets/User/User_Delete?id=" + book.id;
            jumps.submit();
        }
        function user_Rd(event) {
            //翻页
            event = event ? event : window.event;
            var book = event.srcElement ? event.srcElement : event.target;
            jumps.action = "/Servlets/User/User_Record?id=" + book.id;
            jumps.submit();
        }
    </script>
</head>
<body>
<body style="background:transparent;">

<form action="/Servlets/User/User_Search" method="post" name="jumps">
    检索图书：<br>
    <select name="method" id="method">
        <option value="username">姓名</option>
        <option value="gender">性别</option>
    </select>
    <input type="text" id="con" name="context">
    <input type="submit" value="检索">
    <hr>
    <%
        //初始化
        String pageType=(String) request.getSession().getAttribute("pageType");
        if(pageType==null ||!pageType.equals("user")){
            request.getSession().setAttribute("pageType","user");
            request.getSession().setAttribute("page",null);
            request.getSession().setAttribute("method","");
            request.getSession().setAttribute("context","");
        }
        request.setCharacterEncoding("UTF-8");
        Operator operator = (Operator) request.getSession().getAttribute("Operator");
        Reader []readers= bookSearch.searchUser(request);
        //页面
        Pages page_now=Pages.getpage(request,2);

        int tt = page_now.getFlag() * 8 ;
        if(readers.length<=tt &&readers.length>0)
        {
            page_now.setFlag(0);

            request.getSession().setAttribute("page",page_now);
        }
        int bound = page_now.getFlag() * 8 + 8;
        if (readers != null) {
    %>
    <table>
        <thead>
        <tr>
            <th>用户名</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>联系方式</th>
            <th>ID</th>
            <th>生日</th>
            <th>余额</th>
            <th>记录</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int index = page_now.getFlag() * 8; index < readers.length && index < bound;
                 index++) {
        %>
        <tr>
            <td data-label="用户名" name="username"><%=readers[index].getUsername()%>
            </td>
            <td data-label="姓名"><a name="usermodify"
                                   onclick="modify()"><%=readers[index].getName()%></a>
            </td>
            <td data-label="性别"><%=readers[index].getGender()%>
            </td>
            <td data-label="年龄"><%=readers[index].getAge()%>
            </td>
            <td data-label="联系方式"><%=readers[index].getTel()%>
            </td>
            <td data-label="ID"><%=readers[index].getID()%>
            </td>
            <td data-label="生日"><%=readers[index].getBirthday()%>
            </td>
            <td data-label="余额"><%=readers[index].getMoney()%>
            </td>
            <td data-label="借阅记录">
                <input value="借阅记录" type="submit" name="record" onclick="user_Rd()"></td>
            </td>
            <td data-label="删除"> <input value="删除" type="submit"
                                        name="delete" onclick="user_delete()"></td>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%} else {%>
    没有相关用户
    <%
        }
    %>
    <div align="center" style="position:relative;top:10%">
        <%
            if (readers.length > 0)
            //有书
            {
                int flag;
                for (flag = 0; flag <((readers.length%8!=0)?(readers.length /8 + 1):(readers.length/8)); flag++) {
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
</body>
</html>
