package gestionsErreurs;

import java.util.HashMap;

public class MessagesDErreurs {

	private final static HashMap<String, String> map = new HashMap<>();

	static {
		map.put("03", "Probl�me pour acc�der � ce compte client, v�rifier qu'il est bien valide");
		map.put("04", "Le num�ro de compte doit �tre num�rique");
		map.put("05", "Le num�ro de compte doit comporter 4 caract�res");
		map.put("21", "Probl�me d'acc�s � la base de donn�es, veuillez le signaler � votre administrateur");
		map.put("22", "Probl�me apr�s traitement. le traitement a �t� effectu� correctement mais il y a eu un probl�me � signaler � votre administrateur");
		map.put("24", "Op�ration refus�e, d�bit demand� sup�rieur au cr�dit du compte");
	}

	public static String getMessageDErreur(String num) {
		return map.getOrDefault(num, "error " + num);
	}
}
