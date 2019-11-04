package hpn.system.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author alga
 *
 */

@Entity
public class ResponsableMedical {

	private Integer id;
	private String nomPersonnel;
	private String grade;
	private String prenomPersonnel;
	private String sexe;
	private Date derniereModification;
	private Compte myCompte;

	/**
	 * Contructeur Sans Parametre
	 */
	public ResponsableMedical() {
	}

	/**
	 * @param nomPersonnel-type    String correspondant au nom du personnel medical
	 * @param grade-type           String pour stocker la valeur du grade du
	 *                             personnel medical
	 * @param prenomPersonnel-type String correspondant au prenom du personnel
	 *                             medical
	 */
	public ResponsableMedical(String nomPersonnel, String grade, String prenomPersonnel) {
		super();
		this.nomPersonnel = nomPersonnel;
		this.grade = grade;
		this.prenomPersonnel = prenomPersonnel;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomPersonnel() {
		return nomPersonnel;
	}

	public void setNomPersonnel(String nomPersonnel) {
		this.nomPersonnel = nomPersonnel;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPrenomPersonnel() {
		return prenomPersonnel;
	}

	public void setPrenomPersonnel(String prenomPersonnel) {
		this.prenomPersonnel = prenomPersonnel;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	@Temporal(TemporalType.DATE)
	public Date getDerniereModification() {
		return derniereModification;
	}

	public void setDerniereModification(Date derniereModification) {
		this.derniereModification = derniereModification;
	}

	@OneToOne(mappedBy = "myResponsableMedical")
	public Compte getMyCompte() {
		return myCompte;
	}

	public void setMyCompte(Compte myCompte) {
		this.myCompte = myCompte;
	}
}