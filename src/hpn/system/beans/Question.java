package hpn.system.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author alga
 *
 */
@Entity
public class Question {

	private Integer id;
	private String question;

	/**
	 * Constructeur sans parametre
	 */
	public Question() {
	}

	/**
	 * 
	 * @param question-type String valeur de la question
	 */
	public Question(String question) {
		super();
		this.question = question;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}