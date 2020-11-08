package javaBeans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gestionsErreurs.TraitementException;

public class BOperations {

	private final String url = "jdbc:mysql://localhost:3306/banque?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";

	private String noDeCompte, nom, prenom, op, dateInf, dateSup;

	private BigDecimal solde, ancienSolde, nouveauSolde, valeur;

	private Connection connexion;

	private List<String> operations;

	private Statement statement;

	public BOperations() {
		super();
	}

	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = noDeCompte;
	}

	public void ouvrirConnexion() throws TraitementException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connexion = DriverManager.getConnection(url, "BANQUE", "BANQUE");
			connexion.setAutoCommit(false);
			statement = connexion.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println(e.toString());
			throw new TraitementException("01");
		}
	}

	public void fermerConnexion() throws TraitementException {
		try {
			statement.close();
			connexion.close();
		} catch (SQLException e) {
			System.err.println(e.toString());
			throw new TraitementException("02");
		}
	}

	public void consulter() throws TraitementException {
		Objects.requireNonNull(noDeCompte);
		try {
			ResultSet res = statement.executeQuery(
					"SELECT * FROM COMPTE WHERE NOCOMPTE = \"" + noDeCompte + "\";");
			if (res.next()) {
				nom = res.getString("NOM");
				prenom = res.getString("PRENOM");
				solde = res.getBigDecimal("SOLDE");
			} else {
				throw new TraitementException("03");
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
			throw new TraitementException("21");
		}
	}

	public void traiter() throws TraitementException {
		Objects.requireNonNull(noDeCompte);
		try {
			ResultSet res = statement.executeQuery(
					"SELECT * FROM COMPTE WHERE NOCOMPTE = \"" + noDeCompte + "\";");
			if (!res.next())
				throw new TraitementException("03");
			ancienSolde = res.getBigDecimal("SOLDE");
			switch (op) {
			case "+":
				nouveauSolde = ancienSolde.add(valeur);
				break;
			case "-":
				nouveauSolde = ancienSolde.subtract(valeur);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + op);
			}
			if (nouveauSolde.compareTo(BigDecimal.ZERO) < 0)
				throw new TraitementException("24");
			System.out.println(ancienSolde);
			System.out.println(nouveauSolde);
			statement.execute("UPDATE COMPTE SET SOLDE=" + nouveauSolde
					+ " WHERE NOCOMPTE=\"" + noDeCompte + "\";");
			statement.execute("INSERT INTO OPERATION values (" + "\""
					+ noDeCompte + "\"," + "CURDATE()," + "CURTIME(),"
					+ "\"" + op + "\"," + valeur + ");");
			connexion.commit();

		} catch (SQLException e) {
			try {
				connexion.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.toString());
			}
			System.err.println(e.toString());
			throw new TraitementException("21");
		}
	}

	public void listerParDates() throws TraitementException {
		operations = new ArrayList<String>();
		try {
			var res = statement.executeQuery("SELECT * FROM operation where NOCOMPTE=\"" + noDeCompte + "\" "
					+ "AND DATE > '" + dateInf + "' AND DATE < '" + dateSup + "'" + ";");
			while (res.next()) {
				operations.add("{" + res.getDate("date").toString() + res.getTime("heure").toString()
						+ res.getString("op") + res.getBigDecimal("valeur").toString() + "}");
			}
		} catch (SQLException e) {
			System.err.println(e.toString());
			throw new TraitementException("21");
		}
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public BigDecimal getSolde() {
		return solde;
	}

	public BigDecimal getAncienSolde() {
		return ancienSolde;
	}

	public BigDecimal getNouveauSolde() {
		return nouveauSolde;
	}

	public String getValeur() {
		return valeur.toString();
	}

	public String getOp() {
		return op;
	}

	public void setValeur(String valeur) {
		this.valeur = BigDecimal.valueOf(Long.parseLong(valeur));
	}

	public void setOp(String op) {
		this.op = op;
	}

	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}

	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}

	public List<String> getOperations() {
		return operations;
	}
}
