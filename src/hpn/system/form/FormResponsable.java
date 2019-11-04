package hpn.system.form;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hpn.system.beans.Compte;
import hpn.system.beans.ResponsableMedical;
import hpn.system.dao.DAOCompte;
import hpn.system.dao.DAOResponsableMedical;

/**
 * Cestion des données du formulaire d'enregistrement d'un responsable
 * 
 * @author alga
 *
 */
public class FormResponsable {

	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_SEXE = "sexe";
	private static final String CHAMP_GRADE = "grade";

	private static final String CHAMP_LOGIN = "login";
	private static final String CHAMP_PASS = "pwd";
	private static final String CHAMP_TYPE = "type";
	private static final String CHAMP_ID = "id_responsable";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;

	public FormResponsable(HttpServletRequest req) {
		this.request = req;
	}

	/**
	 * 
	 * @param req-type HttpServletRequest Correspondant a la requet envoiyé
	 * @param str-type String Nom du champ depuis le formulaire
	 * @return La valeur du champ
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
	 * Creation et enregistrement d'un responsable
	 * 
	 * @return-type {@link ResponsableMedical}
	 */
	public ResponsableMedical creerResponsable() {

		String nom = getValeur(request, CHAMP_NOM);
		String prenom = getValeur(request, CHAMP_PRENOM);
		String sexe = getValeur(request, CHAMP_SEXE);
		String grade = getValeur(request, CHAMP_GRADE);
		ResponsableMedical responsableMedical = new ResponsableMedical();

		try {
			validerStrNonNull(nom);
		} catch (Exception e) {
			addErreurs(CHAMP_NOM, e.getMessage());
		}
		responsableMedical.setNomPersonnel(nom);

		responsableMedical.setPrenomPersonnel(prenom);

		try {
			validerStrNonNull(sexe);
		} catch (Exception e) {
			addErreurs(CHAMP_SEXE, e.getMessage());
		}
		responsableMedical.setSexe(sexe);

		try {
			validerStrNonNull(grade);
		} catch (Exception e) {
			addErreurs(CHAMP_GRADE, e.getMessage());
		}
		responsableMedical.setGrade(grade);

		responsableMedical.setDerniereModification(new Date());

		return responsableMedical;
	}

	/**
	 * Création d'un compte utilisateur pour un responsable
	 * 
	 * @return
	 */
	public Compte creerCompte() {

		String login = getValeur(request, CHAMP_LOGIN);
		String pass = getValeur(request, CHAMP_PASS);
		String type = getValeur(request, CHAMP_TYPE);

		Compte compte = new Compte();
		ResponsableMedical responsableMedical = new ResponsableMedical();
		DAOCompte daoCompte = new DAOCompte();
		DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();

		try {
			validerStrNonNull(login);

			Compte compte2 = new Compte();
			compte2 = daoCompte.selectOneLogin(login);

			if (compte2.getLogin() != null) {
				addErreurs(CHAMP_LOGIN, "Login déjà existant");
			}

			compte.setLogin(login);

		} catch (Exception e) {
			addErreurs(CHAMP_LOGIN, e.getMessage());
		}

		compte.setType(type);

		try {
			validerPass(pass);
		} catch (Exception e) {
			addErreurs(CHAMP_NOM, pass);
		}
		compte.setPassword(pass);

		try {
			validerStrNonNull(type);
		} catch (Exception e) {
			e.printStackTrace();
		}

		responsableMedical = daoResponsableMedical.selectOne(Integer.parseInt(getValeur(request, CHAMP_ID)));
		compte.setMyResponsableMedical(responsableMedical);

		if (getErreurs().isEmpty()) {
			daoCompte.insert(compte);
		}

		return compte;
	}

	/**
	 * Vérification si la valeur d'un paramettre n'est pas null
	 * @param str
	 * @throws Exception
	 */
	private void validerStrNonNull(String str) throws Exception {
		if (str == null || str.trim().length() == 0) {
			throw new Exception("Ce Champ Ne Doit Pas Etre Null");
		}
	}

	/**
	 * Validation d'un mot de pass
	 * @param str
	 * @throws Exception
	 */
	private void validerPass(String str) throws Exception {
		if (str == null || str.trim().length() < 4) {
			throw new Exception("Mot de pass faible");
		}
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void addErreurs(String str, String err) {
		this.erreurs.put(str, err);
	}
}
