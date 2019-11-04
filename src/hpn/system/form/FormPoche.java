package hpn.system.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import hpn.system.beans.Poche;
import hpn.system.beans.Prelevement;
import hpn.system.dao.DAOPoche;
import hpn.system.dao.DAOPrelevement;

/**
 * Classe permettant la manipulation des données des formulaires
 * d'enregistrement, modification ou suppression d'une poche {@link Poche}
 * 
 * @author alga
 *
 */
public class FormPoche {

	private static final String CHAMP_REFERENCE = "reference";
	private static final String CHAMP_QUANTITE = "quantite";
	private static final String CHAMP_VALIDITE = "validite";
	private static final String CHAMP_PRELEVEMENT = "id_prelevement";
	private static final String CHAMP_DATE = "date_emballage";
	private static final String CHAMP_ID = "id";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;

	/**
	 * Constructeur de la classe qui prend en entré la requete à partir du servlet
	 * 
	 * @param httpServletRequest-type {@link HttpServletRequest}
	 */
	public FormPoche(HttpServletRequest httpServletRequest) {
		this.request = httpServletRequest;
	}

	/**
	 * Fonction permettant la création d'un objet poche
	 * 
	 * @return-type {@link Poche}
	 */
	public Poche creerPoche() {
		Poche poche = new Poche();

		String reference = getValeur(request, CHAMP_REFERENCE);
		String quantite = getValeur(request, CHAMP_QUANTITE);
		String validite = getValeur(request, CHAMP_VALIDITE);
		String idPrelevement = getValeur(request, CHAMP_PRELEVEMENT);
		String dateEmb = getValeur(request, CHAMP_DATE);

		/**
		 * Controle d'une reference de poche, qui doit etre unique
		 */
		try {
			validerRef(reference);
		} catch (Exception e) {
			setErreurs(CHAMP_REFERENCE, e.getMessage());
		}
		poche.setReference(reference);

		/**
		 * Controle sur la quantite qui doit etre strictement positive
		 */
		int qte = 0;
		try {
			qte = Integer.parseInt(quantite);
		} catch (Exception e) {
			setErreurs(CHAMP_QUANTITE, "Erreur Nombre");
		}

		if (qte <= 0) {
			setErreurs(CHAMP_QUANTITE, "Le volume doit etre positif");
		}
		poche.setQuantite(qte);
		Prelevement prelevement = new Prelevement();

		try {

			int idP = Integer.parseInt(idPrelevement);
			DAOPrelevement daoPrelevement = new DAOPrelevement();
			prelevement = daoPrelevement.selectOne(idP);

			poche.setMyPrelevement(prelevement);

		} catch (Exception e) {
			setErreurs(CHAMP_QUANTITE, "Erreur Nombre");
		}

		if (prelevement.getQteRestant() < qte) {
			setErreurs(CHAMP_QUANTITE,
					"Volume trop élévé ! " + prelevement.getQteRestant() + "ml seulement pour ce prélèvement");
		}

		int vdite = 0;
		try {
			vdite = Integer.parseInt(validite);
		} catch (Exception e) {
			setErreurs(CHAMP_VALIDITE, "Erreur Validite");
		}

		if (vdite <= 0) {
			setErreurs(CHAMP_VALIDITE, "Le validité doit un nombre etre positif");
		}
		poche.setValidite(vdite);

		/**
		 * Controle date emballage et date de peremption
		 */

		/**
		 * DateTempo permet type localDateTime de joda qui permet de determiner la date
		 * de peremption apartir de la date d'emballage et la validité
		 **/

		LocalDateTime dateTempo = new LocalDateTime();
		Date dateEmballage = new Date();

		Date dateFin = new Date();

		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-YYYY");

		try {
			dateEmballage = df.parse(dateEmb);

			dateTempo = dtf.parseLocalDateTime(dateEmb);
			dateTempo = dateTempo.plusWeeks(vdite);

			String str = dateTempo.toString(dtf);

			dateFin = df.parse(str);
		} catch (Exception e) {
			setErreurs(CHAMP_DATE, "Erreur date");
		}

		if (dateEmballage.compareTo(new Date()) > 0) {
			setErreurs(CHAMP_DATE, "Cette date n'est pas encore !");
		}

		poche.setDateEmballage(dateEmballage);
		poche.setDatePeremtion(dateFin);

		return poche;
	}

	/**
	 * 
	 * @param req-type {@link HttpServletRequest} Correspondant a la requet envoiyé
	 * @param str-type {@link String} Nom du champ depuis le formulaire
	 * @return-type {@link String} La valeur du champ
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
	 * Controle de l'unicité de la reference
	 * 
	 * @param ref-type {@link String} Valeur du champ reference de la poche
	 * @throws Exception
	 */
	private void validerRef(String ref) throws Exception {

		DAOPoche daoPoche = new DAOPoche();

		if (ref != null && ref.trim().length() != 0) {
			if (daoPoche.selectOneRef(ref).getReference() != null) {
				throw new Exception("Poche Existante !");
			}
		} else {
			throw new Exception("REF Poche Obligatoire");
		}
	}

	/**
	 * @return the erreurs
	 */
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	/**
	 * @param erreurs the erreurs to set
	 */
	public void setErreurs(String champ, String err) {
		this.erreurs.put(champ, err);
	}
}
