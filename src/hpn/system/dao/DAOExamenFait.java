package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.ExamenFait;

public class DAOExamenFait extends IDAO<ExamenFait> {

	public DAOExamenFait() {
		super();
	}

	@Override
	public void insert(ExamenFait obj) {
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
	public void update(ExamenFait obj) {

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
	public List<ExamenFait> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<ExamenFait> examenFaits = new ArrayList<ExamenFait>();

		try {
			Query query = entityManager.createQuery("select E from ExamenFait E order by dateExamen asc");
			examenFaits = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return examenFaits;
	}

	@Override
	public ExamenFait selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		ExamenFait examenFait = new ExamenFait();

		try {
			examenFait = entityManager.find(ExamenFait.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return examenFait;
	}

	@Override
	public List<ExamenFait> selectOne(String mc) {
		return null;
	}

}
