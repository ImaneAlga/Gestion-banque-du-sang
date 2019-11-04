package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Reponse;

public class DAOReponse extends IDAO<Reponse> {

	public DAOReponse() {
		super();
	}
	
	@Override
	public void insert(Reponse obj) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		try {
			entityManager.persist(obj);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
	}

	@Override
	public void update(Reponse obj) {

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
	public List<Reponse> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Reponse> reponses = new ArrayList<Reponse>();

		try {
			Query query = entityManager.createQuery("select R from Reponse R");
			reponses = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return reponses;
	}

	@Override
	public Reponse selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Reponse reponse = new Reponse();

		try {
			reponse = entityManager.find(Reponse.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return reponse;
	}

	@Override
	public List<Reponse> selectOne(String mc) {
		return null;
	}
}
