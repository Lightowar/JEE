package servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gestionsErreurs.TraitementException;
import javaBeans.BOperations;

/**
 * Servlet implementation class SOperations
 */
@WebServlet("/GestionOperations")
public class SOperations extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3583070544823826455L;

	/*
	 * private String buildInfoString(String param) { if (param == null ||
	 * param.length() == 0) return "Le parametre NoDeCompte n'a pas été spécifié";
	 * BOperations bOperations; try { bOperations = new BOperations();
	 * bOperations.ouvrirConnexion(); bOperations.setNoDeCompte(param);
	 * bOperations.consulter(); bOperations.fermerConnexion(); return
	 * "N° de compte: " + param + System.lineSeparator() + "Nom: " +
	 * bOperations.getNom() + System.lineSeparator() + "Prenom: " +
	 * bOperations.getPrenom() + System.lineSeparator() + "Solde: " +
	 * bOperations.getSolde(); } catch (ClassNotFoundException e) { return
	 * e.toString(); } catch (TraitementException e) { return
	 * "Erreur lors du traitement de la demande :" +
	 * MessagesDErreurs.getMessageDErreur(e.getMessage()); } }
	 */

	private BOperations setupBOperations(String param) throws TraitementException {
		if (param == null || param.length() == 0)
			throw new TraitementException("03");
		BOperations bOperations;
		bOperations = new BOperations();
		bOperations.ouvrirConnexion();
		bOperations.setNoDeCompte(param);
		bOperations.consulter();
		bOperations.fermerConnexion();
		return bOperations;
	}

	private void debugParam(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		System.out.println("-------------------session-------------------");
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			System.out.print(paramName + " : ");

			String[] paramValues = request.getParameterValues(paramName);
			for (int i = 0; i < paramValues.length; i++) {
				System.out.println(paramValues[i]);
			}
		}
		System.out.println("---------------------------------------------");
	}

	private void debugSession(HttpSession session) {
		Enumeration<String> params = session.getAttributeNames();
		System.out.println("------------------parameters-----------------");
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			System.out.print(paramName + " : ");

			String paramValue = session.getAttribute(paramName).toString();
			System.out.println(paramValue);
		}
		System.out.println("---------------------------------------------");
	}

	private void traiter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		debugSession(request.getSession());
		try {
			BOperations bo = ((BOperations) request.getSession().getAttribute("bOp"));
			String op = null;
			switch (request.getParameter("op")) {
			case "credit":
				op = "+";
				break;
			case "debit":
				op = "-";
				break;
			default:
				throw new TraitementException("03");
			}
			bo.setOp(op);
			bo.setValeur(request.getParameter("value"));
			bo.ouvrirConnexion();
			bo.traiter();
			bo.consulter();
			bo.fermerConnexion();
			getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
		} catch (TraitementException e) {
			e.printStackTrace();
			request.setAttribute("erreur", e.getMessage());
			getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
		}
	}

	private void finTraitement(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
	}

	private void verifNumeroDeCompte(String numCo) throws TraitementException {
		if (numCo.isEmpty() || !numCo.matches("\\d+"))
			throw new TraitementException("04");
		if (numCo.length() != 4)
			throw new TraitementException("05");
	}

	private void consulter(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String numCo = request.getParameter("NoDeCompte");
			verifNumeroDeCompte(numCo);
			BOperations bo = setupBOperations(numCo);
			request.getSession().setAttribute("NoDeCompte", request.getParameter("NoDeCompte"));
			request.getSession().setAttribute("bOp", bo);
			getServletContext().getRequestDispatcher("/JOperations.jsp").forward(request, response);
		} catch (TraitementException e) {
			request.setAttribute("erreur", e.getMessage());
			getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		finTraitement(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");
		switch (op) {
		case "Traiter":
			traiter(request, response);
			break;
		case "FinDuTraitement":
			finTraitement(request, response);
			break;
		case "Consulter":
			consulter(request, response);
			break;
		default:
			getServletContext().getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
		}
	}

}