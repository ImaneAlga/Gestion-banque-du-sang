package hpn.system.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author alga
 *
 */

@Entity
public class Prelevement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date datePrelevement;
	private Double quantite;
	private Integer tailleDonneur;
	private Double poidsDonneur;
	private Double tensionDonneur;
	private Double qteRestant;
	
	/**
	 * Constructeur sans parametre
	 */
	public Prelevement() {
	}

	/**
	 * Constructeur utilisant les parametres
	 */
	public Prelevement(Date datePrelevement, Double quantite, Donneur myDonneur, List<ExamenFait> myExamenFaits,
			ResponsableMedical myResponsableMedical, List<Poche> myPoches) {
		super();
		this.datePrelevement = datePrelevement;
		this.quantite = quantite;
		this.myDonneur = myDonneur;
		this.myExamenFaits = myExamenFaits;
		this.myResponsableMedical = myResponsableMedical;
		this.myPoches = myPoches;
	}

	/**
	 * Donneur qui a subit le traitement
	 */
	private Donneur myDonneur;

	/**
	 * Les examen effectués avant de valider don du sang
	 * 
	 * @element-type ExamenFait
	 */
	private List<ExamenFait> myExamenFaits;

	/**
	 * Medecin ayant enregistrer le prelevement
	 * 
	 */
	private ResponsableMedical myResponsableMedical;
	/**
	 * Poches dans les quelles on stock le sang apres prélèvement
	 * 
	 * @element-type Poche
	 */
	private List<Poche> myPoches;

	/**
	 * 
	 * @return-type {@link Integer}
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getDatePrelevement() {
		return datePrelevement;
	}

	public void setDatePrelevement(Date date) {
		this.datePrelevement = date;
	}

	public Double getQuantite() {
		return quantite;
	}

	public void setQuantite(Double quantite) {
		this.quantite = quantite;
	}

	@ManyToOne
	@JoinColumn(name = "idDonneur")
	public Donneur getMyDonneur() {
		return myDonneur;
	}

	public void setMyDonneur(Donneur myDonneur) {
		this.myDonneur = myDonneur;
	}

	@OneToMany(mappedBy = "myPrelevement")
	public List<ExamenFait> getMyExamenFaits() {
		return myExamenFaits;
	}

	public void setMyExamenFaits(List<ExamenFait> myExamenFaits) {
		this.myExamenFaits = myExamenFaits;
	}

	@ManyToOne
	@JoinColumn(name = "idResponsableMedical")
	public ResponsableMedical getMyResponsableMedical() {
		return myResponsableMedical;
	}

	public void setMyResponsableMedical(ResponsableMedical myResponsableMedical) {
		this.myResponsableMedical = myResponsableMedical;
	}

	@OneToMany(mappedBy = "myPrelevement")
	public List<Poche> getMyPoches() {
		return myPoches;
	}

	public void setMyPoches(List<Poche> myPoches) {
		this.myPoches = myPoches;
	}

	public Integer getTailleDonneur() {
		return tailleDonneur;
	}

	public void setTailleDonneur(Integer tailleDonneur) {
		this.tailleDonneur = tailleDonneur;
	}

	public Double getPoidsDonneur() {
		return poidsDonneur;
	}

	public void setPoidsDonneur(Double poidsDonneur) {
		this.poidsDonneur = poidsDonneur;
	}

	public Double getTensionDonneur() {
		return tensionDonneur;
	}

	public void setTensionDonneur(Double tensionDonneur) {
		this.tensionDonneur = tensionDonneur;
	}

	public void addElemenent(ExamenFait examenFait) {

		if (this.myExamenFaits == null) {
			this.myExamenFaits = new ArrayList<ExamenFait>();
		} else {
			this.myExamenFaits.add(examenFait);
		}

	}

	public Double getQteRestant() {
		return qteRestant;
	}

	public void setQteRestant(Double qteRestant) {
		this.qteRestant = qteRestant;
	}

}