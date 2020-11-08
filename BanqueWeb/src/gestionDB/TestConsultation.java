package gestionDB;

import gestionsErreurs.MessagesDErreurs;
import gestionsErreurs.TraitementException;
import javaBeans.BOperations;

public class TestConsultation {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			BOperations bOperations = new BOperations();
			bOperations.ouvrirConnexion();
			bOperations.setNoDeCompte("1");
			bOperations.consulter();
			System.out.println(bOperations.getNom() + " " + bOperations.getPrenom() + " " + bOperations.getSolde());
			bOperations.fermerConnexion();
		} catch (TraitementException e) {
			System.out.println(MessagesDErreurs.getMessageDErreur(e.getMessage()));
		}
	}
}
