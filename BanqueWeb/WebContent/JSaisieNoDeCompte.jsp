<%@page import="gestionsErreurs.MessagesDErreurs"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Saisie</title>
</head>
<body>
	<H1>Saisie du N° de compte:</H1>
	<form action="/BanqueWeb/GestionOperations" method="POST">
		<label for="NoDeCompte">
			Entrer le N° de compte: 
			<input type="text" name="NoDeCompte">
		</label>
		<input type="submit" name="operation" value="Consulter">
	</form>
	<%
		String erreur = (String) request.getAttribute("erreur");
	if (erreur != null) {
	%>
	<p><%=MessagesDErreurs.getMessageDErreur(erreur)%></p>
	<%
		}
	%>
</body>
</html>