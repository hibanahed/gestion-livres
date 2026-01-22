<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Add Author</title>
</head>
<body>

<h2>Add Author</h2>

<c:if test="${not empty error}">
    <p style="color: red;">Error: ${error}</p>
</c:if>

<form method="post" action="auteurs">
    <input type="hidden" name="action" value="add" />
    <div>
        <label>Matricule:</label>
        <input type="number" name="matricule" required />
    </div>

    <div>
        <label>Name:</label>
        <input type="text" name="nom" required />
    </div>

    <div>
        <label>First Name:</label>
        <input type="text" name="prenom" required />
    </div>

    <div>
        <label>Gender:</label>
        <select name="genre" required>
            <option value="">Select</option>
            <option value="Masculin">Male</option>
            <option value="Féminin">Female</option>
        </select>
    </div>

    <div>
        <button type="submit">Add</button>
        <a href="auteurs">Cancel</a>
    </div>
</form>

</body>
</html>
