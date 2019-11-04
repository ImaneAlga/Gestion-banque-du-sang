package hpn.system.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author alga
 *
 */

@Entity
public class ExamenFait {

	private Integer id;
	private Date dateExamen;
	private String resultat;
	private TypeExamen myTypeExamen;
	private Prelevement myPrelevement;

	/**
	 * Constructeur sans parametre
	 */
	public ExamenFait() {
	}

	/**
	 * Contructeur using parameters
	 * 
	 * @param dateExamen-type   date valeur date de l'examen
	 * @param resultat-type     String resultat de l'examen
	 * @param myTypeExamen-type TypeExamen type au quel appartient l'examen en cours
	 */
	public ExamenFait(Date dateExamen, String resultat, TypeExamen myTypeExamen) {
		super();
		this.dateExamen = dateExamen;
		this.resultat = resultat;
		this.myTypeExamen = myTypeExamen;
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
	public Date getDateExamen() {
		return dateExamen;
	}

	public void setDateExamen(Date dateExamen) {
		this.dateExamen = dateExamen;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	@ManyToOne
	@JoinColumn(name = "idTypeExamen")
	public TypeExamen getMyTypeExamen() {
		return myTypeExamen;
	}

	public void setMyTypeExamen(TypeExamen myTypeExamen) {
		this.myTypeExamen = myTypeExamen;
	}

	@ManyToOne
	@JoinColumn(name = "idPrelevement")
	public Prelevement getMyPrelevement() {
		return myPrelevement;
	}

	public void setMyPrelevement(Prelevement myPrelevement) {
		this.myPrelevement = myPrelevement;
	}

}