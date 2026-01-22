<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add Book</title>
</head>
<body>

<h2>Add Book</h2>

<c:if test="${not empty error}">
    <p style="color: red;">Error: ${error}</p>
</c:if>

<form method="post" action="livres">
    <input type="hidden" name="action" value="add" />

    <div>
        <label>ISBN:</label>
        <input type="number" name="isbn" required />
    </div>

    <div>
        <label>Title:</label>
        <input type="text" name="titre" required />
    </div>

    <div>
        <label>Description:</label>
        <textarea name="description" rows="4" cols="50"></textarea>
    </div>

    <div>
        <label>Edition Date:</label>
        <input type="date" name="dateEdition" />
    </div>

    <div>
        <label>Publisher:</label>
        <input type="text" name="editeur" />
    </div>

    <div>
        <label>Author:</label>
        <select name="matricule" required>
            <option value="">Select Author</option>
            <c:forEach items="${auteurs}" var="a">
                <option value="${a.matricule}">${a.nom} ${a.prenom}</option>
            </c:forEach>
        </select>
    </div>

    <div>
        <button type="submit">Add</button>
        <a href="livres">Cancel</a>
    </div>
</form>

</body>
</html>
