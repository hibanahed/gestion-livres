<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Gestion des Livres</title>
</head>
<body>

<h1>Book Management System</h1>

<p>
    <a href="index.jsp?lang=fr">FR</a> | <a href="index.jsp?lang=en">EN</a>
</p>

<c:if test="${empty sessionScope.user}">
    <p>Please login to access the application</p>
    <a href="login.jsp">Sign In</a>
</c:if>

<c:if test="${not empty sessionScope.user}">
    <p>Welcome, <strong>${sessionScope.user.login}</strong>!</p>
    <p>Role: <strong>${sessionScope.user.role}</strong></p>
    
    <ul>
        <li><a href="livres">View Books</a></li>
        <c:if test="${sessionScope.user.role == 'Admin'}">
            <li><a href="auteurs">Manage Authors</a></li>
        </c:if>
        <li><a href="logout">Logout</a></li>
    </ul>
</c:if>

</body>
</html>