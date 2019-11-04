package hpn.system.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hpn.system.beans.Compte;
import hpn.system.dao.DAOCompte;

public class FormLogin {

	private static final String CHAMP_USER_NAME = "login";
	private static final String CHAMP_PASSWORD = "password";
	private static final String VUE_SUCCES = "/acceuil";
	private static final String VUE_ECHEC = "/login.jsp";
	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;

	public FormLogin(HttpServletRequest req) {
		this.request = req;
	}

	/**
	 * 
	 * @param req-type {@link HttpServletRequest} Correspondant a la requet envoiyé
	 * @param str-type {@link String} Nom du champ depuis le formulaire
	 * @return {@link String} La valeur du champ
	 */
	private static String getValeur(HttpServletRequest req, String str) {

		String valeur = req.getParameter(str);

		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

	/**
	 * Fonction permettant de verifier si l'identifiant et le mot de pass fournit
	 * correspond à celui d'un compte. Si les valeur correspondent elle retourne un
	 * {@link Compte} sinon elle retourne null
	 * 
	 * @return
	 */
	public Compte validerCompte() {

		DAOCompte daoCompte = new DAOCompte();
		Compte compte = new Compte();

		compte = daoCompte.selectOne(getValeur(request, CHAMP_USER_NAME), getValeur(request, CHAMP_PASSWORD));

		if (compte == null) {
			return null;
		} else {
			return compte;
		}
	}
}
