<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 11.04.2025
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Список користувачів, які додали товари в кошик, але не оплатили його</h1>
<c:forEach var="user" items="${usersInCartList}">
    <div style="width: 40%">
        <p>ID користувача: <span style="font-weight: bold">  ${user.id}</span> </p>
        <p>Username:  <span style="font-weight: bold">${user.username}</span> </p>
        <hr>
        <form action="<%=request.getContextPath() %>/frontController" method="post" class="text-center">
                <input type="hidden" name="action" value="addUserToBlackList" />
                <input type="hidden" name="userId" value="${user.id}" />
                <button type="submit">Додати до чорного списку</button>
        </form>
    </div>
</c:forEach>



</body>
</html>
