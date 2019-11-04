package hpn.system.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author alga
 * 
 *Interface contenant les differents methode pour la manipulation des données persistantes
 *
 * @param <T>-type depandant de la classe qui implémente l'interface
 */
public abstract class IDAO<T> {

	/**
	 * Creation d'une unité de persistence
	 */
	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	public IDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("bloodBank");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public abstract void insert(T obj);
	public abstract void update(T obj);
	public abstract List<T> selectAll();
	public abstract T selectOne(int id);
	public abstract List<T> selectOne(String mc);
	
}
