<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>Book Management System</h2>

<p>
    <a href="login.jsp?lang=fr">FR</a> | <a href="login.jsp?lang=en">EN</a>
</p>

<c:if test="${not empty error}">
    <p style="color: red;"><strong>${error}</strong></p>
</c:if>

<form action="login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="password">
        <c:choose>
            <c:when test="${sessionScope.lang == 'en'}">Password:</c:when>
            <c:otherwise>Mot de passe:</c:otherwise>
        </c:choose>
    </label>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">
        <c:choose>
            <c:when test="${sessionScope.lang == 'en'}">Sign In</c:when>
            <c:otherwise>Se connecter</c:otherwise>
        </c:choose>
    </button>
</form>

<hr>
<h4>Demo Credentials:</h4>
<ul>
    <li>Admin: admin@gmail.com / admin123</li>
    <li>Visitor: visitor@gmail.com / visitor123</li>
</ul>

</body>
</html>