package hpn.system.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author alga
 *
 */
@Entity
public class TypeExamen {

	private Integer id;
	private String nomType;

	/**
	 * 
	 * @element-type GroupeExamen
	 */
	private GroupeExamen myGroupeExamen;

	/**
	 * Constructeur
	 */
	public TypeExamen() {
	}

	/**
	 * 
	 * @param nomType-type        String
	 * @param myGroupeExamen-type String
	 */
	public TypeExamen(String nomType, GroupeExamen myGroupeExamen) {
		super();
		this.nomType = nomType;
		this.myGroupeExamen = myGroupeExamen;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomType() {
		return nomType;
	}

	public void setNomType(String nomType) {
		this.nomType = nomType;
	}

	@ManyToOne
	@JoinColumn(name = "idGroupeExamen")
	public GroupeExamen getMyGroupeExamen() {
		return myGroupeExamen;
	}

	public void setMyGroupeExamen(GroupeExamen myGroupeExamen) {
		this.myGroupeExamen = myGroupeExamen;
	}

}