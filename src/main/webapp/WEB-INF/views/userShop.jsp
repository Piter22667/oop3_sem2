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
    <title>Shop</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4 d-flex justify-content-end">
        <form action="<%= request.getContextPath()%>/frontController" method="get" class="me-2">
            <input type="hidden" name="action" value="redirectToCart">
            <button type="submit"  class="btn btn-outline-primary rounded-pill px-3">
                🛒 Cart
            </button>
        </form>

        <form action="<%= request.getContextPath()%>/frontController" method="post" >
            <input type="hidden" name="action" value="logOut">
            <button type="submit"  class="btn btn-outline-primary rounded-pill px-3">
                Log Out
            </button>
        </form>
</div>




<c:if test="${not empty sessionScope.paymentStatus}">
    <div class="alert alert-success" role="alert">
            ${sessionScope.paymentStatus}
    </div>
    <%
        session.removeAttribute("paymentStatus");
    %>
</c:if>



<div class="container mt-5">
    <h1>Welcome to our Shop!,  ${sessionScope.username}!</h1>
    <p>Number of products: ${productsList.size()}</p>
<%--    <a href="/WEB-INF/views/cart.jsp" class="btn btn-outline-primary position-absolute" style="top: 5em; right: 10em;">🛒 Cart</a>--%>

<%--    <form action="<%= request.getContextPath()%>/frontController" method="get">--%>
<%--        <input type="hidden" name="action" value="userOrderList">--%>
<%--        <button type="submit" class="btn btn-outline-primary position-absolute" style="top: 5em; right: 10em;">--%>
<%--            My orders--%>
<%--        </button>--%>
<%--    </form>--%>

    <div class="row">
        <c:forEach var="product" items="${productsList}">
            <div class="col-md-3 mb-4">
                <div class="product-card card h-100 d-flex flex-column">
                    <div class="card-body d-flex flex-column">
                        <p class="card-title text-center">${product.name}</p>
                        <p class="card-text">${product.description}</p>
                        <p class="card-text ">Price: <strong>${product.price}  &euro;</strong></p>
                        <img src="${product.image_url}" class="img-fluid mb-3" style="max-height:15em"  alt="${product.name}">

                        <form action="<%=request.getContextPath() %>/frontController" method="post">
                            <input type="hidden" name="action" value="addToCart" />
                            <input type="hidden" name="productId" value="${product.id}" />
                            <input type="hidden" name="name" value="${product.name}" />
<%--                            <input type="hidden" name="quantity" value="${product.description}" />--%>
<%--                            встановлюємо hidden quantity на 1 для дебагу--%>
                            <input type="hidden" name="quantity" value="1" />
                            <button type="submit" class="btn btn-primary mt-auto">Додати в кошик</button>
<%--                        <a href="#" class="btn btn-primary mt-auto">Додати в кошик</a>--%>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>




<!--Bootstrap dependencies    -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
