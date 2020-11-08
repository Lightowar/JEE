<%@page import="gestionsErreurs.MessagesDErreurs"%>
<%@page import="javaBeans.BOperations"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Opérations</title>
</head>
<body>
	<span> Numero : <%=request.getSession().getAttribute("NoDeCompte")%><br />
		Non : <%=((BOperations) request.getSession().getAttribute("bOp")).getNom()%><br />
		Prenom : <%=((BOperations) request.getSession().getAttribute("bOp")).getPrenom()%><br />
		Solde : <%=((BOperations) request.getSession().getAttribute("bOp")).getSolde()%><br />
	</span>
	<H1>Opération à effectuer :</H1>
	<form action="/BanqueWeb/GestionOperations" method="POST">
		<input type="radio" id="credit" name="op" value="credit" checked> <label for="credit">Crédit</label>
		<input type="radio" id="debit" name="op" value="debit"> <label for="debit">Débit</label>
		<br/>
		<input type="number" id="value" name="value"/>
		<input type="submit" name="operation" value="Traiter"/>
	</form>
	<form action="/BanqueWeb/GestionOperations" method="POST">
		<input type="submit" name="operation" value="FinDuTraitement">Fin du traitement</input>
	</form>
	<form action="/BanqueWeb/GestionOperations" method="POST">
		<input type="submit" name="operation" value="Consulter"/>
	</form>
	<%
		String erreur = (String) request.getAttribute("erreur");
	if (erreur != null) {
	%><p><%=MessagesDErreurs.getMessageDErreur(erreur)%></p>
	<%
		}
	%>
</body>
</html>