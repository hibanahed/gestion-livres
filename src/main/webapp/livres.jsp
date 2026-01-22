<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Books List</title>
</head>
<body>

<h2>Books List</h2>

<p>User: ${user.login} | Role: ${user.role} | <a href="logout">Logout</a></p>

<c:if test="${user.role == 'Admin'}">
    <p><a href="livres?action=add">Add Book</a> | <a href="auteurs">Manage Authors</a></p>
</c:if>

<h3>Search Books</h3>
<form action="livres" method="get">
    <label for="searchType">Search by:</label>
    <select id="searchType" name="searchType">
        <option value="">-- Select --</option>
        <option value="titre">Title</option>
        <option value="auteur">Author (Matricule)</option>
        <option value="date">Date Range</option>
    </select>

    <div id="searchValueGroup" style="display: none;">
        <label for="searchValue">Value:</label>
        <input type="text" id="searchValue" name="searchValue">
    </div>

    <div id="dateGroup" style="display: none;">
        <label for="dateDebut">From:</label>
        <input type="date" name="dateDebut">
        <label for="dateFin">To:</label>
        <input type="date" name="dateFin">
    </div>

    <button type="submit">Search</button>
</form>

<script>
    document.getElementById('searchType').addEventListener('change', function() {
        var type = this.value;
        document.getElementById('searchValueGroup').style.display = (type === 'date') ? 'none' : 'block';
        document.getElementById('dateGroup').style.display = (type === 'date') ? 'block' : 'none';
    });
</script>

<table border="1">
    <tr>
        <th>ISBN</th>
        <th>Title</th>
        <th>Description</th>
        <th>Date</th>
        <th>Publisher</th>
        <th>Author</th>
        <th>Actions</th>
    </tr>

    <c:forEach items="${livres}" var="l">
        <tr>
            <td>${l.isbn}</td>
            <td>${l.titre}</td>
            <td>${l.description}</td>
            <td>${l.dateEdition}</td>
            <td>${l.editeur}</td>
            <td>${l.auteur.nom} ${l.auteur.prenom}</td>
            <td>
                <c:if test="${user.role == 'Admin'}">
                    <a href="delete-livre?isbn=${l.isbn}" onclick="return confirm('Delete?');">Delete</a> |
                    <a href="edit-livre?isbn=${l.isbn}">Edit</a>
                </c:if>
                <c:if test="${user.role != 'Admin'}">
                    (View only)
                </c:if>
            </td>
        </tr>
    </c:forEach>

</table>

<br>
<a href="index.jsp">Back</a>

</body>
</html>
