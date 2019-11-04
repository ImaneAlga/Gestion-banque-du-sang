package hpn.system.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Receveur {

	private Integer id;
	private String nomReceveur;
	private String prenomReceveur;
	private String groupeSanguin;
	private String facteurRhesus;
	private String adresse;
	private String sexe;
	private Date dateNaiss;
	private Integer etat;

	/**
	 * 
	 * @element-type Liste_Transfert transfert consernant le receveur
	 */
	private List<Transfert> myTransferts;

	/**
	 * Constructeur sans parametres
	 */
	public Receveur() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomReceveur() {
		return nomReceveur;
	}

	public void setNomReceveur(String nomReceveur) {
		this.nomReceveur = nomReceveur;
	}

	public String getPrenomReceveur() {
		return prenomReceveur;
	}

	public void setPrenomReceveur(String prenomReceveur) {
		this.prenomReceveur = prenomReceveur;
	}

	public String getGroupeSanguin() {
		return groupeSanguin;
	}

	public void setGroupeSanguin(String groupeSanguin) {
		this.groupeSanguin = groupeSanguin;
	}

	@OneToMany(mappedBy = "myReceveur")
	public List<Transfert> getMyTransferts() {
		return myTransferts;
	}

	public void setMyTransferts(List<Transfert> myTransferts) {
		this.myTransferts = myTransferts;
	}

	public String getFacteurRhesus() {
		return facteurRhesus;
	}

	public void setFacteurRhesus(String facteurRhesus) {
		this.facteurRhesus = facteurRhesus;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	@Temporal(TemporalType.DATE)
	public Date getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}

	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}

}