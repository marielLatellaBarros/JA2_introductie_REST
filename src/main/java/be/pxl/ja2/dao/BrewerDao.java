package be.pxl.ja2.dao;

import be.pxl.ja2.data.Brewer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class BrewerDao {
	private EntityManager entityManager;

	public BrewerDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Brewer> findByCity(String city) {
		TypedQuery<Brewer> findByCity = entityManager.createNamedQuery("findByCity", Brewer.class);
		findByCity.setParameter("city", city);
		
		return findByCity.getResultList();
	}

	public Brewer saveBrewer(Brewer brewer) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(brewer);
		tx.commit();
		return brewer;
	}
}
