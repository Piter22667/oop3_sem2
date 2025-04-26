<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 05.04.2025
  Time: 2:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Add new Product</title>
  </head>
  <body>
  <h1>Add New Product</h1>
  <form action="<%=request.getContextPath() %>/frontController" method="post">
<%--    <input type="hidden" name="action" value="createProduct">--%>

    <label for="name">Product Name:</label>
<%--    <input name="action" type="hidden" value="Product" />--%>
    <input type="text" name="name" id="name" required /><br/>

    <label for="description">Description:</label>
    <input type="text" name="description" id="description" required /><br/>

    <label for="price">Price:</label>
    <input type="number" name="price" id="price" required /><br/>

    <label for="quantity">Quantity: </label>
    <input type="text" name="quantity" id="quantity" required /><br/>

    <label for="image_url">Image URL:</label>
    <input type="text" name="image_url" id="image_url" required /><br/>

    <input type="hidden" name="action" value="createProduct" /> <!-- Додаємо прихований інпут для дії -->
    <button type="submit">Add product</button>

  </form>

  </body>
</html>
