package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Poche;

public class DAOPoche extends IDAO<Poche> {

	public DAOPoche() {
		super();
	}

	@Override
	public void insert(Poche obj) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		obj.setEtat(1);
		try {
			entityManager.persist(obj);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
	}

	@Override
	public void update(Poche obj) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		try {
			entityManager.merge(obj);

			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();

		}

	}

	@Override
	public List<Poche> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Poche> poches = new ArrayList<Poche>();

		try {
			Query query = entityManager.createQuery("select P from Poche P where P.etat = 1");
			poches = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return poches;
	}
	
	public List<Poche> selectAllTransferer() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Poche> poches = new ArrayList<Poche>();

		try {
			Query query = entityManager.createQuery("select P from Poche P where P.etat = 2");
			poches = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return poches;
	}

	public List<Poche> selectAllInvalide() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Poche> poches = new ArrayList<Poche>();
		try {
			Query query = entityManager.createQuery(
					"select P from Poche P where P.datePeremtion < current_date() and P.etat = 1");
			poches = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return poches;
	}

	@Override
	public Poche selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Poche poche = new Poche();

		try {
			poche = entityManager.find(Poche.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return poche;
	}

	@Override
	public List<Poche> selectOne(String mc) {
		return null;
	}

	public Poche selectOneRef(String mc) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Poche poche = new Poche();

		try {
			Query query = entityManager.createQuery("select P from Poche P where P.reference= :l");
			query.setParameter("l", mc);
			poche = (Poche) query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return poche;
	}

	public void delete(Poche obj) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		obj.setEtat(0);
		try {
			entityManager.merge(obj);

			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();

		}

	}
	
	/**
	 *  
	 * @param obj-type {@link Poche} 
	 */
	public void transferer(Poche obj) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		obj.setEtat(2);
		try {
			entityManager.merge(obj);

			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();

		}
	}
	
	public List<Poche> selectAdv(String gs, String rs) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Poche> poches = new ArrayList<Poche>();
		try {
			Query query = entityManager.createQuery(
					"select P from Poche P where P.myPrelevement.myDonneur.groupeSanguin = :g and P.myPrelevement.myDonneur.facteurRhesus = :r and P.etat = 1");
			query.setParameter("g", gs);
			query.setParameter("r", rs);
			poches = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		return poches;
	}
}
