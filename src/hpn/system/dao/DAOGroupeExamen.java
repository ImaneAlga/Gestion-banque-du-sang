package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.GroupeExamen;

public class DAOGroupeExamen extends IDAO<GroupeExamen> {

	public DAOGroupeExamen() {
		super();
	}

	@Override
	public void insert(GroupeExamen obj) {
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
	public void update(GroupeExamen obj) {

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
	public List<GroupeExamen> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<GroupeExamen> groupeExamens = new ArrayList<GroupeExamen>();

		try {
			Query query = entityManager.createQuery("select G from GroupeExamen G");
			groupeExamens = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return groupeExamens;
	}

	@Override
	public GroupeExamen selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		GroupeExamen groupeExamen = new GroupeExamen();

		try {
			groupeExamen = entityManager.find(GroupeExamen.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return groupeExamen;
	}

	@Override
	public List<GroupeExamen> selectOne(String mc) {
		return null;
	}

	public GroupeExamen selectOneName(String mc) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		GroupeExamen groupeExamen = new GroupeExamen();

		try {
			Query query = entityManager.createQuery("select E from GroupeExamen E where E.nomGroupe= :l");
			query.setParameter("l", mc);
			groupeExamen = (GroupeExamen) query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return groupeExamen;
	}
}
