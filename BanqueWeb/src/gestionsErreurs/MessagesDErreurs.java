package gestionsErreurs;

import java.util.HashMap;

public class MessagesDErreurs {

	private final static HashMap<String, String> map = new HashMap<>();

	static {
		map.put("03", "Problème pour accéder à ce compte client, vérifier qu'il est bien valide");
		map.put("04", "Le numéro de compte doit être numérique");
		map.put("05", "Le numéro de compte doit comporter 4 caractères");
		map.put("21", "Problème d'accès à la base de données, veuillez le signaler à votre administrateur");
		map.put("22", "Problème après traitement. le traitement a été effectué correctement mais il y a eu un problème à signaler à votre administrateur");
		map.put("24", "Opération refusée, débit demandé supérieur au crédit du compte");
	}

	public static String getMessageDErreur(String num) {
		return map.getOrDefault(num, "error " + num);
	}
}
