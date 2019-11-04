package hpn.system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import hpn.system.beans.Compte;

public class DAOCompte extends IDAO<Compte> {

	public DAOCompte() {
		super();
	}

	@Override
	public void insert(Compte obj) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		obj.setStatut("actif");
		try {
			entityManager.persist(obj);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
	}

	@Override
	public void update(Compte obj) {

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
	public List<Compte> selectAll() {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Compte> comptes = new ArrayList<Compte>();

		try {
			Query query = entityManager.createQuery("select C from Compte C where C.statut = 'actif'");
			comptes = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return comptes;
	}

	@Override
	public Compte selectOne(int id) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Compte compte = new Compte();

		try {
			compte = entityManager.find(Compte.class, id);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}

		return compte;
	}

	@Override
	public List<Compte> selectOne(String mc) {
		return null;
	}

	public Compte selectOne(String user, String psd) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Compte compte = new Compte();

		try {
			Query query = entityManager
					.createQuery("select C from Compte C where C.login= :l and C.password= :p and C.statut='actif'");
			query.setParameter("l", user);
			query.setParameter("p", psd);
			compte = (Compte) query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return compte;
	}
	
	public Compte selectOneLogin(String user) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Compte compte = new Compte();

		try {
			Query query = entityManager
					.createQuery("select C from Compte C where C.login= :l and C.statut='actif'");
			query.setParameter("l", user);
			compte = (Compte) query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
		}
		return compte;
	}
	

}
