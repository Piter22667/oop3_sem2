<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 04.04.2025
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2 style="text-align: center;">Login</h2>
<form action="/login" method="post">
    <table style="margin: 0 auto;">
        <tr>
            <td>Enter name:</td>
            <td><input type="text" name="username" value="${username}" /></td>
        </tr>
        <tr>
            <td>Enter password:</td>
            <td><input type="password" name="password" value="${password}"/></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <br>
                <input type="submit" value="Login" />
                <input type="reset" value="Reset" />
                <hr>
                <p style="font-style: italic">${error}</p>

            </td>
        </tr>
    </table>

</form>
</body>
</html>
