package hpn.system.beans;

import java.io.Serializable;
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
 * @author alga ; etat = 1 active, 0 suprimée, 2 transferer
 */

@Entity
public class Poche implements Serializable{

	private static final long serialVersionUID = 2357269282829457902L;
	private Integer id;
	private String reference;
	private Integer quantite;
	private Date dateEmballage;
	private Date datePeremtion;
	private Integer etat;
	private Integer validite;

	/**
	 * Constructeur sans parametre
	 */
	public Poche() {
	}

	/***
	 * Constructor Using parameter
	 */
	public Poche(String reference, Integer quantite, Date dateEmballage, Date datePeremtion, Integer etat,
			Prelevement myPrelevement, Transfert myTransfert) {
		super();
		this.reference = reference;
		this.quantite = quantite;
		this.dateEmballage = dateEmballage;
		this.datePeremtion = datePeremtion;
		this.etat = etat;
		this.myPrelevement = myPrelevement;
		this.myTransfert = myTransfert;
	}

	/**
	 * @element-type Prelevement Prélèvement ayant produit la poche
	 */
	private Prelevement myPrelevement;
	/**
	 * 
	 * @element-type Transfert Correspond a l'objet tranfert, si la poche a été
	 *               tranferer
	 */
	private Transfert myTransfert;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer qte) {
		this.quantite = qte;
	}

	@Temporal(TemporalType.DATE)
	public Date getDateEmballage() {
		return dateEmballage;
	}

	public void setDateEmballage(Date dateEmballage) {
		this.dateEmballage = dateEmballage;
	}

	@Temporal(TemporalType.DATE)
	public Date getDatePeremtion() {
		return datePeremtion;
	}

	public void setDatePeremtion(Date datePeremtion) {
		this.datePeremtion = datePeremtion;
	}

	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}

	@ManyToOne
	@JoinColumn(name = "idPrelevement")
	public Prelevement getMyPrelevement() {
		return myPrelevement;
	}

	public void setMyPrelevement(Prelevement myPrelevement) {
		this.myPrelevement = myPrelevement;
	}

	@OneToOne(mappedBy = "myPoche")
	public Transfert getMyTransfert() {
		return myTransfert;
	}

	public void setMyTransfert(Transfert myTransfert) {
		this.myTransfert = myTransfert;
	}

	public Integer getValidite() {
		return validite;
	}

	public void setValidite(Integer validite) {
		this.validite = validite;
	}

}