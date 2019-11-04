package hpn.system.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author alga
 *
 */
@Entity
public class Transfert {

	private Integer id;
	private Date dateLivraison;
	private String motif;
	private Poche myPoche;
	private Receveur myReceveur;
	private ResponsableMedical myResponsableMedical;

	/**
	 * Constructeur sans parametre
	 */
	public Transfert() {
	}

	/**
	 * Constructeur using parameters
	 * 
	 * @param dateLivraison             -type Date
	 * @param motif-type                String
	 * @param myPoches-type             String
	 * @param myReceveur-type           String
	 * @param myResponsableMedical-type String
	 */
	public Transfert(Date dateLivraison, String motif, Poche myPoche, Receveur myReceveur,
			ResponsableMedical myResponsableMedical) {
		super();
		this.dateLivraison = dateLivraison;
		this.motif = motif;
		this.myPoche = myPoche;
		this.myReceveur = myReceveur;
		this.myResponsableMedical = myResponsableMedical;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	@OneToOne
	@JoinColumn(name = "idPoche")
	public Poche getMyPoche() {
		return myPoche;
	}

	public void setMyPoche(Poche myPoche) {
		this.myPoche = myPoche;
	}

	@ManyToOne
	@JoinColumn(name = "idReceveur")
	public Receveur getMyReceveur() {
		return myReceveur;
	}

	public void setMyReceveur(Receveur myReceveur) {
		this.myReceveur = myReceveur;
	}

	@ManyToOne
	@JoinColumn(name = "idResponsableMedical")
	public ResponsableMedical getMyResponsableMedical() {
		return myResponsableMedical;
	}

	public void setMyResponsableMedical(ResponsableMedical myResponsableMedical) {
		this.myResponsableMedical = myResponsableMedical;
	}

}