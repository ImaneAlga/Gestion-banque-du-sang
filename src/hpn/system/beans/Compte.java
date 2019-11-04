package hpn.system.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author alga
 *
 */

@Entity
public class Compte {

	private Integer id;
	private String login;
	private String password;
	private String type;
	private String statut;

	private ResponsableMedical myResponsableMedical;

	/***
	 * Constructeur sans parametre
	 */
	public Compte() {
	}

	/**
	 * 
	 * @param login-type                String correspondant au nom d'utilisateur du
	 *                                  propriétaire du compte
	 * @param password-type             String correspondant au mot de passe du
	 *                                  compte qui sera crypté
	 * @param type-type                 String correspondant au statut du compte par
	 *                                  example Admin,...
	 * @param myResponsableMedical-type ResponsableMedical variable correspondant
	 *                                  Responsable Medical proprietaite du compte
	 */
	public Compte(String login, String password, String type, ResponsableMedical myResponsableMedical) {
		super();
		this.login = login;
		this.password = password;
		this.type = type;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToOne
	public ResponsableMedical getMyResponsableMedical() {
		return myResponsableMedical;
	}

	public void setMyResponsableMedical(ResponsableMedical myResponsableMedical) {
		this.myResponsableMedical = myResponsableMedical;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}