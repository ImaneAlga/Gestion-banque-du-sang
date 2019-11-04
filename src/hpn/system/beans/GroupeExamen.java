package hpn.system.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * @author alga
 *
 */
@Entity
public class GroupeExamen {

	private Integer id;
	private String nomGroupe;
	private List<TypeExamen> myTypeExamens;

	/**
	 * Constructeur sans parametre
	 */
	public GroupeExamen() {
	}

	/**
	 * Contructeur sans parametre
	 * 
	 * @param nomGroupe-type String nom du groupe
	 */
	public GroupeExamen(String nomGroupe) {
		super();
		this.nomGroupe = nomGroupe;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomGroupe() {
		return nomGroupe;
	}

	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	@OneToMany(mappedBy = "myGroupeExamen")
	public List<TypeExamen> getMyTypeExamens() {
		return myTypeExamens;
	}

	public void setMyTypeExamens(List<TypeExamen> myTypeExamens) {
		this.myTypeExamens = myTypeExamens;
	}

}