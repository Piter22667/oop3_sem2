<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 11.04.2025
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>BlackList</title>
</head>
<body>
<br>
<hr>
<hr>
<h1>Список користувачів, які знаходяться в чорному списку</h1>

<c:forEach var="blackListed" items="${blackListedUsers}">
  <div style="width: 40%">
    <p>ID користувача: ${blackListed.userId}</p>
    <p>Username ${blackListed.addedAt}</p>
    <br>
  </div>
</c:forEach>
</body>
</html>
