package hpn.system.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hpn.system.beans.Receveur;

/**
 * Gestion des données issues du formulaire d'enregistreement d'un objet {@link Receveur}
 * @author alga
 *
 */
public class FormReceveur {

	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_GROUPE_S = "groupe_sanguin";
	private static final String CHAMP_FACTEUR_R = "facteur_rhesus";
	private static final String CHAMP_DATE = "date_naiss";
	private static final String CHAMP_ADRESSE = "adresse";
	private static final String CHAMP_SEXE = "sexe";
	private static final String CHAMP_ID = "id";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;

	public FormReceveur(HttpServletRequest req) {
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

	public Receveur creerReceveur() {

		String nom = getValeur(request, CHAMP_NOM);
		String prenom = getValeur(request, CHAMP_PRENOM);
		String dateNaiss = getValeur(request, CHAMP_DATE);
		String groupeSanguin = getValeur(request, CHAMP_GROUPE_S);
		String facteurRhesus = getValeur(request, CHAMP_FACTEUR_R);
		String adresse = getValeur(request, CHAMP_ADRESSE);
		String sexe = getValeur(request, CHAMP_SEXE);

		Receveur receveur = new Receveur();

		/**
		 * Validation du nom
		 */
		try {
			validerStrNonNull(nom);
		} catch (Exception e) {
			putErreurs(CHAMP_NOM, e.getMessage());
		}
		receveur.setNomReceveur(nom);
		receveur.setPrenomReceveur(prenom);

		/**
		 * Validation de l'adresse
		 */
		try {
			validerStrNonNull(adresse);
		} catch (Exception e) {
			putErreurs(CHAMP_ADRESSE, e.getMessage());
		}
		receveur.setAdresse(adresse);

		/**
		 * Validation De Sexe
		 */
		try {
			validerStrNonNull(sexe);
		} catch (Exception e) {
			putErreurs(CHAMP_NOM, e.getMessage());
		}
		receveur.setSexe(sexe);

		/**
		 * Validation De Date De Naissance
		 */

		try {
			validerDateNaiss(dateNaiss);
		} catch (Exception e) {
			putErreurs(CHAMP_DATE, e.getMessage());
		}

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			receveur.setDateNaiss(dateFormat.parse(dateNaiss));
		} catch (Exception e1) {
			putErreurs(CHAMP_DATE, "Erreur Format ");
		}

		try {
			validerStrNonNull(groupeSanguin);
		} catch (Exception e) {
			putErreurs(CHAMP_GROUPE_S, e.getMessage());
		}
		receveur.setGroupeSanguin(groupeSanguin);

		try {
			validerStrNonNull(facteurRhesus);
		} catch (Exception e) {
			putErreurs(CHAMP_FACTEUR_R, e.getMessage());
		}
		receveur.setFacteurRhesus(facteurRhesus);

		return receveur;
	}

	/**
	 * Verfier si la valeur d'un paramettre n'est pas null 
	 * @param str
	 * @throws Exception Valeur du paramettre est null
	 */
	private void validerStrNonNull(String str) throws Exception {
		if (str == null || str.trim().length() == 0) {
			throw new Exception("Ce Champ Ne Doit Pas Etre Null");
		}
	}

	/**
	 * Controle et validation de la date de naissance du receveur
	 * @param str
	 * @throws Exception
	 */
	private void validerDateNaiss(String str) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateFormat.parse(str);
		} catch (Exception e) {
			throw new Exception("Date Incorrecte !");
		}
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void putErreurs(String champ, String erreur) {
		this.erreurs.put(champ, erreur);
	}
}
