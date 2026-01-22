<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Edit Author</title>
</head>
<body>

<h2>Edit Author</h2>

<form method="post" action="edit-auteur">
    <input type="hidden" name="matricule" value="${auteur.matricule}" />

    <div>
        <label>Name:</label>
        <input type="text" name="nom" value="${auteur.nom}" required />
    </div>

    <div>
        <label>First Name:</label>
        <input type="text" name="prenom" value="${auteur.prenom}" required />
    </div>

    <div>
        <label>Gender:</label>
        <select name="genre" required>
            <option value="Masculin" <c:if test="${auteur.genre == 'Masculin'}">selected</c:if>>Male</option>
            <option value="Féminin" <c:if test="${auteur.genre == 'Féminin'}">selected</c:if>>Female</option>
        </select>
    </div>

    <div>
        <button type="submit">Edit</button>
        <a href="auteurs">Cancel</a>
    </div>
</form>

</body>
</html>
