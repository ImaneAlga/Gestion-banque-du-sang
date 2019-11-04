package hpn.system.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import hpn.system.beans.Donneur;
import hpn.system.dao.DAODonneur;

/**
 * Classe permettant le controle et l'enregistrement des données issues du
 * formulaire d'enregistrement d'undonneur
 * 
 * @author alga
 *
 */
public class FormDonneur {

	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_CNI = "cni";
	private static final String CHAMP_GROUPE_S = "groupe_sanguin";
	private static final String CHAMP_FACTEUR_R = "facteur_rhesus";
	private static final String CHAMP_DATE = "date_naiss";
	private static final String CHAMP_NOMBRE_DON = "nombre_don";
	private static final String CHAMP_ADRESSE = "adresse";
	private static final String CHAMP_TEL = "telephone";
	private static final String CHAMP_RELIGION = "religion";
	private static final String CHAMP_STATUT_MATRIMONIAL = "statut_matrimonial";
	private static final String CHAMP_SEXE = "sexe";
	private static final String CHAMP_ID = "id";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;

	/**
	 * 
	 * @param req {@link HttpServletRequest} Requete du servlet de creation
	 */
	public FormDonneur(HttpServletRequest req) {
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
	 * Fonction permettant la creation d'un donneur
	 * 
	 * @return type {@link Donneur}
	 */
	public Donneur creerDonneur() {

		String nom = getValeur(request, CHAMP_NOM);
		String prenom = getValeur(request, CHAMP_PRENOM);
		String cni = getValeur(request, CHAMP_CNI);
		String dateNaiss = getValeur(request, CHAMP_DATE);
		String groupeSanguin = getValeur(request, CHAMP_GROUPE_S);
		String facteurRhesus = getValeur(request, CHAMP_FACTEUR_R);
		String adresse = getValeur(request, CHAMP_ADRESSE);
		String telephone = getValeur(request, CHAMP_TEL);
		String sexe = getValeur(request, CHAMP_SEXE);
		String nbDon = getValeur(request, CHAMP_NOMBRE_DON);
		String religion = getValeur(request, CHAMP_RELIGION);
		String statutMatrimonial = getValeur(request, CHAMP_STATUT_MATRIMONIAL);

		Donneur donneur = new Donneur();

		/**
		 * Validation du nom
		 */
		try {
			validerStrNonNull(nom);
		} catch (Exception e) {
			putErreurs(CHAMP_NOM, e.getMessage());
		}
		donneur.setNom(nom);
		donneur.setPrenom(prenom);

		/**
		 * Validation du numero CNI
		 */
		try {
			validerCNI(cni);
		} catch (Exception e) {
			putErreurs(CHAMP_CNI, e.getMessage());
		}
		donneur.setNumeroCNI(cni);

		/**
		 * @exception exception declenchée lors que l'adresse du donneur n'est pas
		 *                      renseignée
		 */
		try {
			validerStrNonNull(adresse);
		} catch (Exception e) {
			putErreurs(CHAMP_ADRESSE, e.getMessage());
		}
		donneur.setAdresse(adresse);

		/**
		 * Validation De Sexe
		 */
		try {
			validerStrNonNull(sexe);
		} catch (Exception e) {
			putErreurs(CHAMP_NOM, e.getMessage());
		}
		donneur.setSexe(sexe);

		/**
		 * Validation du champ religion
		 */
		try {
			validerStrNonNull(religion);
		} catch (Exception e) {
			putErreurs(CHAMP_RELIGION, e.getMessage());
		}
		donneur.setReligion(religion);

		/**
		 * Validation du champ statut matrimonial
		 */
		try {
			validerStrNonNull(statutMatrimonial);
		} catch (Exception e) {
			putErreurs(CHAMP_STATUT_MATRIMONIAL, e.getMessage());
		}
		donneur.setStatutMatrimonial(statutMatrimonial);

		/**
		 * Validation du champ date de naissance
		 */

		try {
			validerDateNaiss(dateNaiss);
		} catch (Exception e) {
			putErreurs(CHAMP_DATE, e.getMessage());
		}

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			donneur.setDateNaiss(dateFormat.parse(dateNaiss));
		} catch (Exception e1) {
			putErreurs(CHAMP_DATE, "Erreur Format ");
		}

		LocalDateTime dateTempo = new LocalDateTime();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-YYYY");

		try {
			dateTempo = dtf.parseLocalDateTime(dateNaiss);
			int age = Years.yearsBetween(dateTempo, LocalDateTime.now()).getYears();
			if (age < 18) {
				putErreurs(CHAMP_DATE, "Age Donneur(" + age + ") Est Insuffisant");
			}

		} catch (Exception e) {
			e.printStackTrace();
			putErreurs(CHAMP_DATE, "Erreur date2");
		}

		try {
			validerStrNonNull(groupeSanguin);
		} catch (Exception e) {
			putErreurs(CHAMP_GROUPE_S, e.getMessage());
		}
		donneur.setGroupeSanguin(groupeSanguin);

		try {
			validerStrNonNull(facteurRhesus);
		} catch (Exception e) {
			putErreurs(CHAMP_FACTEUR_R, e.getMessage());
		}
		donneur.setFacteurRhesus(facteurRhesus);

		try {
			int n = Integer.parseInt(nbDon);
			donneur.setNombreDon(n);

			if (n < 0) {
				putErreurs(CHAMP_NOMBRE_DON, "Nombre Don Incorrect");
			}

		} catch (Exception e) {
			putErreurs(CHAMP_NOMBRE_DON, "Nombre Incorrect");
		}

		try {
			validerTel(telephone);
		} catch (Exception e) {
			putErreurs(CHAMP_TEL, e.getMessage());
		}
		donneur.setTelephone(telephone);

		return donneur;
	}

	/**
	 * Modification d'un formation d'un donneur
	 * 
	 * @return-type {@link Donneur}
	 */
	public Donneur modifierDonneur() {

		String nom = getValeur(request, CHAMP_NOM);
		String prenom = getValeur(request, CHAMP_PRENOM);
		String cni = getValeur(request, CHAMP_CNI);
		String adresse = getValeur(request, CHAMP_ADRESSE);
		String telephone = getValeur(request, CHAMP_TEL);
		String nbDon = getValeur(request, CHAMP_NOMBRE_DON);
		String id = getValeur(request, CHAMP_ID);
		String religion = getValeur(request, CHAMP_RELIGION);
		String statutMatrimonial = getValeur(request, CHAMP_STATUT_MATRIMONIAL);

		Donneur donneur = new Donneur();
		DAODonneur daoDonneur = new DAODonneur();

		try {
			int var = Integer.parseInt(id);
			donneur = daoDonneur.selectOne(var);

			try {
				validerCNI(cni, var);
			} catch (Exception e) {
				putErreurs(CHAMP_CNI, e.getMessage());
			}
			donneur.setNumeroCNI(cni);

		} catch (Exception e) {

		}

		try {
			validerStrNonNull(nom);
		} catch (Exception e) {
			putErreurs(CHAMP_NOM, e.getMessage());
		}
		donneur.setNom(nom);
		donneur.setPrenom(prenom);
		donneur.setEtat(1);

		try {
			validerStrNonNull(adresse);
		} catch (Exception e) {
			putErreurs(CHAMP_ADRESSE, e.getMessage());
		}
		donneur.setAdresse(adresse);

		/**
		 * Validation du champ religion
		 */
		try {
			validerStrNonNull(religion);
		} catch (Exception e) {
			putErreurs(CHAMP_RELIGION, e.getMessage());
		}
		donneur.setReligion(religion);

		/**
		 * Validation du champ statut matrimonial
		 */
		try {
			validerStrNonNull(statutMatrimonial);
		} catch (Exception e) {
			putErreurs(CHAMP_STATUT_MATRIMONIAL, e.getMessage());
		}
		donneur.setStatutMatrimonial(statutMatrimonial);

		try {
			int n = Integer.parseInt(nbDon);
			donneur.setNombreDon(n);
		} catch (Exception e) {
			putErreurs(CHAMP_NOMBRE_DON, "Nombre Incorrect");
		}

		try {
			validerTel(telephone);
		} catch (Exception e) {
			putErreurs(CHAMP_TEL, e.getMessage());
		}
		donneur.setTelephone(telephone);

		return donneur;
	}

	/**
	 * 
	 * @param cni Valeur du champ CNI
	 * @throws Exception Fonction permettant de verifier, lors de la création si le
	 *                   champ CNI est remplie et que la valeur saisie est unique
	 */
	private void validerCNI(String cni) throws Exception {

		DAODonneur daoDonneur = new DAODonneur();

		if (cni != null && cni.trim().length() != 0) {
			if (daoDonneur.selectOneCNI(cni).getNumeroCNI() != null) {
				throw new Exception("Donneur Existant !");
			}
		} else {
			throw new Exception("CNI du Donneur Obligatoire");
		}
	}

	/**
	 * 
	 * @param cni Valeur du champ CNI
	 * @param id  Identifiant du donneur dont on enregistre
	 * @throws Exception Fonction permettant de verifier, lors de la modification si
	 *                   le champ CNI est remplie et que la valeur saisie est unique
	 */
	private void validerCNI(String cni, int id) throws Exception {

		DAODonneur daoDonneur = new DAODonneur();

		if (cni != null && cni.trim().length() != 0) {
			if (daoDonneur.selectOneCNI(cni).getId() != null && daoDonneur.selectOneCNI(cni).getId() != id) {
				throw new Exception("Donneur Existant !");
			}
		} else {
			throw new Exception("CNI du Donneur Obligatoire");
		}
	}

	/**
	 * 
	 * @param str Valeur du champ nom à varifier
	 * @throws Exception Fonction permettant de verifier si un champ est nul ou pas
	 */
	private void validerStrNonNull(String str) throws Exception {
		if (str == null || str.trim().length() == 0) {
			throw new Exception("Ce Champ Ne Doit Pas Etre Null");
		}
	}

	/**
	 * 
	 * @param str Valeur du champ date de naissance
	 * @throws Exception exception generer en cas d'erreur de saisie ou l'age faible
	 *                   du donneur
	 */
	private void validerDateNaiss(String str) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateFormat.parse(str);
		} catch (Exception e) {
			throw new Exception("Date Incorrecte !");
		}
	}

	/**
	 * 
	 * @param telephone type String valeur du champ numero de telephone
	 * @throws Exception exception declanché en cas d'erreur de saisie du numero
	 *                   d'un donneur
	 */
	private void validerTel(String telephone) throws Exception {
		if (telephone != null) {
			if (!telephone.matches("^\\d+$")) {
				throw new Exception("Le numéro de téléphone doit uniquement contenir des chiffres.");
			} else if (telephone.length() < 9) {
				throw new Exception("Le numéro de téléphone doit contenir au moins 9 chiffres.");
			}
		} else {
			throw new Exception("Merci d'entrer un numéro de téléphone.");
		}
	}

	/**
	 * 
	 * Fonction permettant de retourner les erreurs effectuées lors du remplissage
	 * du formulaire concernant un donneur
	 */
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	/**
	 * 
	 * @param champ  type String champ de l'erreur
	 * @param erreur type String message d'erreur
	 */
	public void putErreurs(String champ, String erreur) {
		this.erreurs.put(champ, erreur);
	}
}
