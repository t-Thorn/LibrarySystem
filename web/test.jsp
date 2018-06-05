<%@ page import="henu.dao.Pages" %>
<%@ page import="henu.dao.impl.book.pages.book" %>
<%@ page import="henu.bean.Borrow" %><%--
  Created by IntelliJ IDEA.
  User: 72705
  Date: 2018-03-29
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    </script>
</head>
<body>

<c:out value="&lt要显示的数据对象（未使用转义字符）&gt" escapeXml="true" default="默认值"></c:out><br/>
<c:out value="&lt要显示的数据对象（使用转义字符）&gt" escapeXml="false" default="默认值"></c:out><br/>
<c:out value="${null}" escapeXml="false">使用的表达式结果为null，则输出该默认值</c:out><br/>
</body>
</html>
