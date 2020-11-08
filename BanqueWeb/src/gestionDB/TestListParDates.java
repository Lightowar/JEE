package gestionDB;

import gestionsErreurs.MessagesDErreurs;
import gestionsErreurs.TraitementException;
import javaBeans.BOperations;

public class TestListParDates {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			BOperations bOperations = new BOperations();
			bOperations.ouvrirConnexion();
			bOperations.setNoDeCompte("1");
			bOperations.setDateInf("2020-10-01");
			bOperations.setDateSup("2020-10-31");
			bOperations.listerParDates();
			bOperations.getOperations().forEach(System.out::println);
			bOperations.fermerConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDErreur(e.getMessage()));
		}
	}
}
