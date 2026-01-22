<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Authors List</title>
</head>
<body>

<h2>Authors List</h2>

<p>User: ${user.login} | Role: ${user.role} | <a href="logout">Logout</a></p>

<p><a href="auteurs?action=add">Add Author</a> | <a href="livres">Back to Books</a></p>

<table border="1">
    <tr>
        <th>Matricule</th>
        <th>Name</th>
        <th>First Name</th>
        <th>Gender</th>
        <th>Actions</th>
    </tr>

    <c:forEach items="${auteurs}" var="a">
        <tr>
            <td>${a.matricule}</td>
            <td>${a.nom}</td>
            <td>${a.prenom}</td>
            <td>${a.genre}</td>
            <td>
                <a href="edit-auteur?matricule=${a.matricule}">Edit</a> |
                <a href="delete-auteur?matricule=${a.matricule}" onclick="return confirm('Delete?');">Delete</a>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
