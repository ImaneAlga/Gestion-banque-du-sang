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

/**
 * 
 * @author alga
 *
 */

@Entity
public class Donneur {

	private Integer id;
	private String numeroCNI;
	private String nom;
	private String prenom;
	private Date dateNaiss;
	private String groupeSanguin;
	private String facteurRhesus;
	private String sexe;
	private Integer etat;
	private String adresse;
	private String telephone;
	private Integer nombreDon;
	private Date dateDernierDon;
	private String statutMatrimonial;
	private String religion;
	
	/**
	 * Contructeur sans parametre
	 */
	public Donneur() {}

	/**
	 * Constructeur utilisant les attributs
	 */
	public Donneur(String numeroCNI, String nom, String prennom, Date dateNaiss, String groupeSanguin, String sexe,
			Integer etat, String adresse, String telephone, Integer nombreDon, List<Prelevement> myPrelevements,
			List<Reponse> myReponses) {
		super();
		this.numeroCNI = numeroCNI;
		this.nom = nom;
		this.prenom = prennom;
		this.dateNaiss = dateNaiss;
		this.groupeSanguin = groupeSanguin;
		this.sexe = sexe;
		this.etat = etat;
		this.adresse = adresse;
		this.telephone = telephone;
		this.nombreDon = nombreDon;
		this.myPrelevements = myPrelevements;
		this.myReponses = myReponses;
	}

	/**
	 * Liste des prélèvements subit par un donneur
	 */
	
	private List<Prelevement> myPrelevements;
	/**
	 * Les reponses au questions posées au donneur lors de l'entretient
	 */
	private List<Reponse> myReponses;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroCNI() {
		return numeroCNI;
	}

	public void setNumeroCNI(String numeroCNI) {
		this.numeroCNI = numeroCNI;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prennom) {
		this.prenom = prennom;
	}

	@Temporal(TemporalType.DATE)
	public Date getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Date date) {
		this.dateNaiss = date;
	}

	public String getGroupeSanguin() {
		return groupeSanguin;
	}

	public void setGroupeSanguin(String groupeSanguin) {
		this.groupeSanguin = groupeSanguin;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}


	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getNombreDon() {
		return nombreDon;
	}

	public void setNombreDon(Integer nombreDon) {
		this.nombreDon = nombreDon;
	}

	@OneToMany(mappedBy = "myDonneur")
	public List<Prelevement> getMyPrelevements() {
		return myPrelevements;
	}

	public void setMyPrelevements(List<Prelevement> myPrelevements) {
		this.myPrelevements = myPrelevements;
	}

	@OneToMany(mappedBy = "myDonneur")
	public List<Reponse> getMyReponses() {
		return myReponses;
	}

	public void setMyReponses(List<Reponse> myReponses) {
		this.myReponses = myReponses;
	}

	public String getFacteurRhesus() {
		return facteurRhesus;
	}

	public void setFacteurRhesus(String facteurRhesus) {
		this.facteurRhesus = facteurRhesus;
	}

	@Temporal(TemporalType.DATE)
	public Date getDateDernierDon() {
		return dateDernierDon;
	}

	public void setDateDernierDon(Date dateDernierDon) {
		this.dateDernierDon = dateDernierDon;
	}

	public String getStatutMatrimonial() {
		return statutMatrimonial;
	}

	public void setStatutMatrimonial(String statutMatrimonial) {
		this.statutMatrimonial = statutMatrimonial;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

}