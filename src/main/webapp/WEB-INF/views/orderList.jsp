<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 11.04.2025
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User orders list</title>
</head>
<body>

<c:forEach var="order" items="${ordersList}">
    <div style="width: 40%">
        <p>ID користувача: ${order.userId}</p>
        <p>Час оформлення запмовлення: ${order.orderDate}</p>
        <p>Загальна вартість замовленяя: ${order.totalPrice}</p>
        <hr>
    </div>
</c:forEach>

</body>
</html>
