package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.ResponsableMedical;

public class DAOResponsableMedical extends IDAO<ResponsableMedical> {
	
	public DAOResponsableMedical() {
		super();
	}
	
	@Override
	public void insert(ResponsableMedical obj) {
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
	public void update(ResponsableMedical obj) {

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
	public List<ResponsableMedical> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<ResponsableMedical> responsableMedicals = new ArrayList<ResponsableMedical>();
		
		try {
			Query query = entityManager.createQuery("select G from ResponsableMedical G");
			responsableMedicals = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return responsableMedicals;
	}

	@Override
	public ResponsableMedical selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		ResponsableMedical responsableMedical = new ResponsableMedical();

		try {
			responsableMedical = entityManager.find(ResponsableMedical.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return responsableMedical;
	}

	@Override
	public List<ResponsableMedical> selectOne(String mc) {
		return null;
	}
}
