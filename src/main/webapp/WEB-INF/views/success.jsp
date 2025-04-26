<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 10.04.2025
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Success</title>
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="text-center mb-4">
        <h2 class="text-success">Дякуємо за замовлення!</h2>
        <p class="text-muted">Ось товари, які ви придбали:</p>
    </div>

    <div class="row">
        <c:forEach var="item" items="${orderItems}">
            <div class="col-md-4 mb-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title">ID товару: ${item.productId}</h5>
                        <p class="card-text">
                            Кількість: ${item.quantity}<br>
                            Ціна за одиницю: ${item.price} ₴<br>
                            Загальна: ${item.quantity * item.price} ₴
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="text-center mt-4">
        <a href="home.jsp" class="btn btn-primary">Повернутись на головну</a>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>