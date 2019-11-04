package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.TypeExamen;

public class DAOTypeExamen extends IDAO<TypeExamen> {

	public DAOTypeExamen() {
		super();
	}
	
	@Override
	public void insert(TypeExamen obj) {
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
	public void update(TypeExamen obj) {

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
	public List<TypeExamen> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<TypeExamen> typeExamens = new ArrayList<TypeExamen>();

		try {
			Query query = entityManager.createQuery("select T from TypeExamen T");
			typeExamens = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return typeExamens;
	}

	@Override
	public TypeExamen selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypeExamen typeExamen = new TypeExamen();

		try {
			typeExamen = entityManager.find(TypeExamen.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return typeExamen;
	}

	@Override
	public List<TypeExamen> selectOne(String mc) {
		return null;
	}
	
	public TypeExamen selectOneName(String mc) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		TypeExamen typeExamen = new TypeExamen();

		try {
			Query query = entityManager.createQuery("select E from TypeExamen E where E.nomType= :l");
			query.setParameter("l", mc);
			typeExamen = (TypeExamen) query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return typeExamen;
	}
}
