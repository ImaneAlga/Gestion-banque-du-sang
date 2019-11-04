package hpn.system.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import hpn.system.beans.Donneur;
import hpn.system.beans.Prelevement;
import hpn.system.beans.ResponsableMedical;
import hpn.system.dao.DAODonneur;
import hpn.system.dao.DAOResponsableMedical;

/**
 * Controle et enregistrement d'un prélevement
 * 
 * @author alga
 *
 */
public class FormPrelevement {

	private static final String CHAMP_RESPONSABLE = "id_responsable";
	private static final String CHAMP_DATE_PRELEVEMENT = "date_prelevement";
	private static final String CHAMP_QUANTITE = "quantite";
	private static final String CHAMP_DONNEUR = "id_donneur";
	private static final String CHAMP_EXAMEN = "examen_fait";
	private static final String CHAMP_ID = "id";
	private static final String CHAMP_TAILLE = "taille_donneur";
	private static final String CHAMP_TENSION = "tension_donneur";
	private static final String CHAMP_POIDS = "poids_donneur";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;
	private double qte = 0;
	private int idDonneur;
	private int idResponsable;

	/**
	 * Constructeur
	 * 
	 * @param req-type {@link HttpServletRequest}
	 */
	public FormPrelevement(HttpServletRequest req) {
		this.request = req;
	}

	/**
	 * Retourne la valeur contenu du paramettre de la requette
	 * 
	 * @param req-type {@link HttpServletRequest}
	 * @param str-type {@link String} nom du paramettre
	 * @return
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
	 * Création et persistence d'un objet {@link Prelevement}
	 * 
	 * @return-type {@link Prelevement}
	 */
	public Prelevement creerPrelevement() {

		String responsable = getValeur(request, CHAMP_RESPONSABLE);
		String dateP = getValeur(request, CHAMP_DATE_PRELEVEMENT);
		String quantite = getValeur(request, CHAMP_QUANTITE);
		String donneur = getValeur(request, CHAMP_DONNEUR);

		DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
		DAODonneur daoDonneur = new DAODonneur();

		Prelevement prelevement = new Prelevement();

		try {
			qte = Float.parseFloat(quantite);
		} catch (Exception e) {
			addErreurs(CHAMP_QUANTITE, "Quantité Invalide");
		}
		prelevement.setQuantite(qte);

		Donneur donneur2 = new Donneur();
		if (donneur != null && donneur.trim().length() != 0) {
			try {
				idDonneur = Integer.parseInt(donneur);

				donneur2 = daoDonneur.selectOne(idDonneur);
				prelevement.setMyDonneur(donneur2);

			} catch (Exception e) {
				addErreurs(CHAMP_DONNEUR, "Donneur Invalide");
			}
		} else {
			addErreurs(CHAMP_DONNEUR, "Donneur Invalide");
		}

		/**
		 * Verification de capacité à donner à partir du dernier don Si le dernier don
		 * n'a pas fait 3 moins il n'est pas possible de donnés
		 */

		if (donneur2.getDateDernierDon() != null) {

			LocalDate dateTempo = new LocalDate();
			DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-YYYY");

			try {
				dateTempo = dtf.parseLocalDate(dateP);

				int periode = Days
						.daysBetween(LocalDate.fromDateFields(donneur2.getDateDernierDon()), dateTempo)
						.getDays();

				if (periode < 90) {
					addErreurs(CHAMP_DATE_PRELEVEMENT,
							"Son dernier don, il y a " + periode + " jrs n'a pas attein 3 mois");
				}

			} catch (Exception e) {
				e.printStackTrace();
				addErreurs(CHAMP_DATE_PRELEVEMENT, "Erreur date2");
			}
		}
		
		
		/**
		 * @exception La taille de l'utilisateur invalide
		 */

		Integer taille = 0;
		try {
			taille = Integer.parseInt(getValeur(request, CHAMP_TAILLE));
			if (taille <= 0) {
				addErreurs(CHAMP_TAILLE, "la taille doit avoir une valeur positive");
			}
		} catch (Exception e) {
			addErreurs(CHAMP_DONNEUR, "Taille Invalide");
		}
		prelevement.setTailleDonneur(taille);

		/**
		 * @exception La valeur de la masse du donneur invalide
		 */
		Double poids = 0.0;
		try {
			poids = Double.parseDouble(getValeur(request, CHAMP_POIDS));
			if (poids < 50.0) {
				addErreurs(CHAMP_POIDS, "poids du donneur faible pour un don");
			}
		} catch (Exception e) {
			addErreurs(CHAMP_POIDS, "poids invalide");
		}
		prelevement.setPoidsDonneur(poids);

		/**
		 * @exception Cette exception est déclenché lors que la valeur de la tension est
		 *                  invalide
		 */
		Double tension = 0.0;
		try {
			tension = Double.parseDouble(getValeur(request, CHAMP_TENSION));
			if (tension < 0.0) {
				addErreurs(CHAMP_TENSION, "tension du donneur faible pour un don");
			}
		} catch (Exception e) {
			addErreurs(CHAMP_TENSION, "tension invalide");
		}
		prelevement.setTensionDonneur(tension);

		if (responsable != null && responsable.trim().length() != 0) {
			try {
				idResponsable = Integer.parseInt(responsable);

				ResponsableMedical responsableMedical = new ResponsableMedical();
				responsableMedical = daoResponsableMedical.selectOne(idResponsable);
				prelevement.setMyResponsableMedical(responsableMedical);

			} catch (Exception e) {
				addErreurs(CHAMP_DONNEUR, "Donneur Invalide");
			}
		} else {
			addErreurs(CHAMP_DONNEUR, "Donneur Invalide");
		}

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			prelevement.setDatePrelevement(dateFormat.parse(dateP));

			if (dateFormat.parse(dateP).compareTo(new Date()) > 0) {
				addErreurs(CHAMP_DATE_PRELEVEMENT, "Cette date n'est pas encore !");
			}

		} catch (ParseException e) {
			addErreurs(CHAMP_DATE_PRELEVEMENT, e.getMessage());
		}

		return prelevement;
	}

	/**
	 * @param str
	 * @throws Exception
	 */
	private void validerStrNonNull(String str) throws Exception {
		if (str == null || str.trim().length() == 0) {
			throw new Exception("Ce Champ Ne Doit Pas Etre Null");
		}
	}

	/**
	 * 
	 * @param str-type {@link String} Valeur du champ date prelevement
	 * @throws Exception Format de la date est incorrect
	 */
	private void validerDate(String str) throws Exception {
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

	public void addErreurs(String val, String err) {
		this.erreurs.put(val, err);
	}
}
