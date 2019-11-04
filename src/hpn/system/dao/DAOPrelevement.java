package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Prelevement;

public class DAOPrelevement extends IDAO<Prelevement> {

	public DAOPrelevement() {
		super();
	}

	@Override
	public void insert(Prelevement obj) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		obj.setQteRestant(obj.getQuantite());
		
		try {
			entityManager.persist(obj);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
	}

	@Override
	public void update(Prelevement obj) {

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
	public List<Prelevement> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Prelevement> prelevements = new ArrayList<Prelevement>();

		try {
			Query query = entityManager.createQuery("select P from Prelevement P");
			prelevements = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return prelevements;
	}

	@Override
	public Prelevement selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Prelevement prelevement = new Prelevement();

		try {
			prelevement = entityManager.find(Prelevement.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return prelevement;
	}

	@Override
	public List<Prelevement> selectOne(String mc) {
		return null;
	}
}
