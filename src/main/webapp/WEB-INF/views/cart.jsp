<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 07.04.2025
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Ваш кошик</h2>
<h1>Welcome to cart, ${sessionScope.username}!</h1>
<c:if test="${not empty cartList}">
    <c:forEach var="cart" items="${cartList}">
        <div style="width: 40%">
            <p>${cart.productName}</p>
            <p>Кількість: ${cart.quantity}</p>
            <p>Ціна за 1-цю: ${cart.price}</p>
            <hr>
        </div>
    </c:forEach>
    <br>
    <h3>Загальна вартість обраних товарів: ${totalPrice}</h3>

    <form action="${pageContext.request.contextPath}/frontController" method="post">
        <input type="hidden" name="action" value="checkout">
        <button type="submit" value="Оформити замовлення"> Оформити замовлення</button>
    </form>
</c:if>
<c:if test="${empty cartList}">
    <p>Кошик порожній.</p>
</c:if>




</body>
</html>
