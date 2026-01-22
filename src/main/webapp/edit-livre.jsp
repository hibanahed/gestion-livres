<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit Book</title>
</head>
<body>

<h2>Edit Book</h2>

<form method="post" action="edit-livre">
    <input type="hidden" name="isbn" value="${livre.isbn}" />

    <div>
        <label>Title:</label>
        <input type="text" name="titre" value="${livre.titre}" required />
    </div>

    <div>
        <label>Description:</label>
        <textarea name="description" rows="4" cols="50">${livre.description}</textarea>
    </div>

    <div>
        <label>Edition Date:</label>
        <input type="date" name="dateEdition" value="${livre.dateEdition}" />
    </div>

    <div>
        <label>Publisher:</label>
        <input type="text" name="editeur" value="${livre.editeur}" />
    </div>

    <div>
        <label>Author:</label>
        <select name="matricule" required>
            <c:forEach items="${auteurs}" var="a">
                <option value="${a.matricule}" 
                    <c:if test="${a.matricule == livre.auteur.matricule}">selected</c:if>>
                    ${a.nom} ${a.prenom}
                </option>
            </c:forEach>
        </select>
    </div>

    <div>
        <button type="submit">Edit</button>
        <a href="livres">Cancel</a>
    </div>
</form>

</body>
</html>
