package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Donneur;

public class DAODonneur extends IDAO<Donneur> {

	public DAODonneur() {
		super();
	}
	
	@Override
	public void insert(Donneur obj) {
		obj.setEtat(1);

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
	public void update(Donneur obj) {

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
	public List<Donneur> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Donneur> donneurs = new ArrayList<Donneur>();

		try {
			Query query = entityManager.createQuery("select C from Donneur C where C.etat != 0");
			donneurs = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return donneurs;
	}

	@Override
	public Donneur selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Donneur donneur = new Donneur();

		try {
			donneur = entityManager.find(Donneur.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return donneur;
	}

	@Override
	public List<Donneur> selectOne(String mc) {
		return null;
	}
	
	public Donneur selectOneCNI(String mc) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Donneur donneur = new Donneur();

		try {
			Query query = entityManager.createQuery("select D from Donneur D where D.numeroCNI= :l");
			query.setParameter("l", mc);
			donneur = (Donneur) query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return donneur;
	}
	
	public void delete(Donneur obj) {

		obj.setEtat(0);
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		try {
			entityManager.merge(obj);

			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();

		}

	}
	
}
