package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Receveur;

public class DAOReceveur extends IDAO<Receveur> {
	
	public DAOReceveur() {
		super();
	}
	
	@Override
	public void insert(Receveur obj) {
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
	public void update(Receveur obj) {

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
	public List<Receveur> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Receveur> receveurs = new ArrayList<Receveur>();

		try {
			Query query = entityManager.createQuery("select R from Receveur R where R.etat = 1");
			receveurs = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return receveurs;
	}

	@Override
	public Receveur  selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Receveur receveur = new Receveur();

		try {
			receveur = entityManager.find(Receveur.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return receveur;
	}
	
	public void delete(Receveur obj) {

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
	
	@Override
	public List<Receveur> selectOne(String mc) {
		return null;
	}
}
