<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 04.04.2025
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>







<html>
<head>
    <title>Admin Panel</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>


<div class="container mt-4 d-flex justify-content-end">
    <form action="<%= request.getContextPath()%>/frontController" method="post">
        <input type="hidden" name="action" value="logOut">
        <button type="submit"  class="btn btn-outline-primary rounded-pill px-3">
            Log Out
        </button>
    </form>
</div>


<div class="container mt-5">

<h1>Welcome to admin panel, ${sessionScope.username}!</h1>
<div class="row">
    <c:forEach var="product" items="${productsList}">
        <div class="col-md-3 mb-4">
            <div class="product-card card h-100 d-flex flex-column">
                <div class="card-body d-flex flex-column">
                    <p class="card-title fw-bold text-center">${product.name}</p>
                    <p class="card-text">${product.description}</p>
                    <p class="card-text ">Price: <strong>${product.price}  &euro;</strong></p>
                    <p class="card-text ">Quantity: <strong>${product.quantity}</strong></p>
                    <img src="${product.image_url}" class="img-fluid mb-3" alt="${product.name}">

<%--                    редагування товару--%>
                    <form action="<%=request.getContextPath() %>/frontController" method="get" class="mt-auto">
                        <input type="hidden" name="action" value="updateProduct" />
                        <input type="hidden" name="id" value="${product.id}" />
                        <button type="submit" class="btn btn-warning mx-auto d-block">Редагувати</button>
                    </form>

                    <!-- видалення товару -->
                    <form action="<%=request.getContextPath() %>/frontController" method="post" class="mt-auto">
                        <input type="hidden" name="action" value="deleteProduct" />
                        <input type="hidden" name="id" value="${product.id}" />
                        <button  type="submit" class="btn btn-danger mx-auto d-block">Видалити</button>
                    </form>
<%--                    <a href="#" class="btn btn-primary mt-auto">Додати в кошик</a>--%>
                </div>
            </div>
        </div>
    </c:forEach>

</div>

    <div style="display: flex; flex-direction: row; align-items: center; gap: 20px; padding-left: 40px;">
<%--    додавання товару--%>
    <form action="<%=request.getContextPath() %>/frontController" method="get" class="text-center">
        <input type="hidden" name="action" value="addProduct" />
        <button type="submit" class="btn btn-success" >Додати новий товар</button>
    </form>

    <form action="<%=request.getContextPath() %>/frontController" method="get" class="text-center">
        <input type="hidden" name="action" value="orderList" />
        <button type="submit" class="btn btn-success" >Список замовлень користувачів</button>
    </form>

    <form action="<%=request.getContextPath() %>/frontController" method="post">
        <input type="hidden" name="action" value="manageBlackList" />
        <button type="submit" class="btn btn-success" >Керувати користувачами які не здійснили оплату (Black list users)</button>
    </form>

    <form action="<%=request.getContextPath() %>/frontController" method="get" class="text-center">
        <input type="hidden" name="action" value="getAllUsersFromBlackList" />
        <button type="submit" class="btn btn-success" >Список заблокованих користувачівв</button>
    </form>
    </div>

</div>

<!--Bootstrap dependencies    -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
