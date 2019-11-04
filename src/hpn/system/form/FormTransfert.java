package hpn.system.form;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hpn.system.beans.Poche;
import hpn.system.beans.Receveur;
import hpn.system.beans.ResponsableMedical;
import hpn.system.beans.Transfert;
import hpn.system.dao.DAOPoche;
import hpn.system.dao.DAOReceveur;
import hpn.system.dao.DAOResponsableMedical;

/**
 * Gestion des données du formulaire de transfert
 * 
 * @author alga
 *
 */
public class FormTransfert {

	private static final String CHAMP_RESPONSABLE = "id_responsable";
	private static final String CHAMP_DATE = "date_sortie";
	private static final String CHAMP_RECEVEUR = "id_receveur";
	private static final String CHAMP_POCHE = "id_poche";
	private static final String CHAMP_ID = "id";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private HttpServletRequest request;
	private double qte = 0;
	private int idReceveur;
	private int idResponsable;
	private int idPoche;
	private String gsReceveur;
	private String gsDonneur;

	public FormTransfert(HttpServletRequest req) {
		this.request = req;
	}

	private static String getValeur(HttpServletRequest req, String str) {

		String valeur = req.getParameter(str);

		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

	/**
	 * Création d'un objet transfert
	 * 
	 * @return
	 */
	public Transfert creerTransfert() {

		String responsable = getValeur(request, CHAMP_RESPONSABLE);
		String dateS = getValeur(request, CHAMP_DATE);
		String poche = getValeur(request, CHAMP_POCHE);
		String receveur = getValeur(request, CHAMP_RECEVEUR);

		DAOResponsableMedical daoResponsableMedical = new DAOResponsableMedical();
		DAOReceveur daoReceveur = new DAOReceveur();
		DAOPoche daoPoche = new DAOPoche();

		Transfert transfert = new Transfert();

		if (receveur != null && receveur.trim().length() != 0) {
			try {
				idReceveur = Integer.parseInt(receveur);

				Receveur receveur2 = new Receveur();
				receveur2 = daoReceveur.selectOne(idReceveur);
				transfert.setMyReceveur(receveur2);

				gsReceveur = receveur2.getGroupeSanguin();

			} catch (Exception e) {
				addErreurs(CHAMP_RECEVEUR, "Donneur Invalide");
			}
		} else {
			addErreurs(CHAMP_RECEVEUR, "Receveur Invalide");
		}

		if (responsable != null && responsable.trim().length() != 0) {
			try {
				idResponsable = Integer.parseInt(responsable);

				ResponsableMedical responsableMedical = new ResponsableMedical();
				responsableMedical = daoResponsableMedical.selectOne(idResponsable);
				transfert.setMyResponsableMedical(responsableMedical);
			} catch (Exception e) {
				addErreurs(CHAMP_RESPONSABLE, "Responsable Invalide");
			}
		} else {
			addErreurs(CHAMP_RECEVEUR, "Receveur Invalide");
		}

		if (poche != null && poche.trim().length() != 0) {
			try {
				idPoche = Integer.parseInt(poche);

				Poche poche2 = new Poche();
				poche2 = daoPoche.selectOne(idPoche);
				transfert.setMyPoche(poche2);

				gsDonneur = poche2.getMyPrelevement().getMyDonneur().getGroupeSanguin();

			} catch (Exception e) {
				addErreurs(CHAMP_RESPONSABLE, "Responsable Invalide");
			}
		} else {
			addErreurs(CHAMP_RECEVEUR, "Receveur Invalide");
		}

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		try {
			transfert.setDateLivraison(dateFormat.parse(dateS));
		} catch (Exception e) {
			addErreurs(CHAMP_DATE, "Erreur Date");
		}

		if (gsDonneur != null && gsReceveur != null) {
			if (!gsDonneur.equals(gsReceveur)) {
				addErreurs(CHAMP_POCHE, "Incompatibilité avec le receveur ");
				addErreurs(CHAMP_RECEVEUR, "Incompatibilité avec le donneur ");

			}
		}

		return transfert;
	}

	/**
	 * Verification de la valeur d'un paramettre
	 * 
	 * @param str
	 * @throws Exception
	 */
	private void validerStrNonNull(String str) throws Exception {
		if (str == null || str.trim().length() == 0) {
			throw new Exception("Ce Champ Ne Doit Pas Etre Null");
		}
	}

	/**
	 * Validation du champ date
	 * 
	 * @param str
	 * @throws Exception
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
