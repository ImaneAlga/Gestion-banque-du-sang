package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Transfert;

public class DAOTranfert extends IDAO<Transfert> {

	public DAOTranfert() {
		super();
	}
	
	@Override
	public void insert(Transfert obj) {
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
	public void update(Transfert obj) {

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
	public List<Transfert> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Transfert> transferts = new ArrayList<Transfert>();

		try {
			Query query = entityManager.createQuery("select T from Transfert T");
			transferts = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return transferts;
	}

	@Override
	public Transfert selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Transfert transfert = new Transfert();

		try {
			transfert = entityManager.find(Transfert.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return transfert;
	}

	@Override
	public List<Transfert> selectOne(String mc) {
		return null;
	}
}
