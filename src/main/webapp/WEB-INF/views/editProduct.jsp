<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 05.04.2025
  Time: 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit products</title>
</head>
<body>
<h1>Edit Product</h1>
<form action="<%=request.getContextPath() %>/frontController" method="post">
  <input type="hidden" name="action" value="editProduct" />
  <input type="hidden" name="id" value="${product.id}" />

  <label for="name">Product Name:</label>
  <input type="text" name="name" id="name" value="${product.name}" required /><br/>

  <label for="description">Description:</label>
  <input type="text" name="description" id="description" value="${product.description}" required /><br/>

  <label for="price">Price:</label>
  <input type="number" name="price" id="price" value="${product.price}" required /><br/>


  <label for="quantity">Quantity: </label>
  <input type="text" name="quantity" id="quantity" value="${product.quantity}" /><br/>

  <label for="imageUrl">Image URL:</label>
  <input type="text" name="image_url" id="imageUrl" value="${product.image_url}" required /><br/>

  <input type="submit" value="Update Product" />

</form>
</body>
</html>
