package hpn.system.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author alga
 *
 */

@Entity
public class Reponse {

	private Integer id;
	private String valeur;
	private Date dateDernierModif;
	private Question myQuestion;
	private Donneur myDonneur;

	/**
	 * Constructeur sans parametre
	 */
	public Reponse() {
	}

	/**
	 * 
	 * @param valeur-type           String
	 * @param dateDernierModif-type Date
	 * @param myQuestion-type       Question
	 * @param myDonneur-type        Donneur
	 */
	public Reponse(String valeur, Date dateDernierModif, Question myQuestion, Donneur myDonneur) {
		super();
		this.valeur = valeur;
		this.dateDernierModif = dateDernierModif;
		this.myQuestion = myQuestion;
		this.myDonneur = myDonneur;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	@Temporal(TemporalType.DATE)
	public Date getDateDernierModif() {
		return dateDernierModif;
	}

	public void setDateDernierModif(Date dateDernierModif) {
		this.dateDernierModif = dateDernierModif;
	}

	@ManyToOne
	public Question getMyQuestion() {
		return myQuestion;
	}

	public void setMyQuestion(Question myQuestion) {
		this.myQuestion = myQuestion;
	}

	@ManyToOne
	public Donneur getMyDonneur() {
		return myDonneur;
	}

	public void setMyDonneur(Donneur myDonneur) {
		this.myDonneur = myDonneur;
	}

}