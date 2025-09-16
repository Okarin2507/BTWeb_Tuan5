package vn.week3.dao;

import vn.week3.model.Category;
import vn.week3.model.User;
import vn.week3.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.io.File;

public class CategoryDAO {

	public void add(Category category) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(category);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void update(Category category) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(category);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}


	public void delete(int id, String uploadPath) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Category category = em.find(Category.class, id);
			if (category != null) {
				if (category.getIcon() != null && !category.getIcon().isEmpty()) {
					File fileToDelete = new File(uploadPath + File.separator + category.getIcon());
					if (fileToDelete.exists()) {
						fileToDelete.delete();
					}
				}
				em.remove(category);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public Category findById(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c JOIN FETCH c.user WHERE c.id = :id",
					Category.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public List<Category> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c JOIN FETCH c.user", Category.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}

	public List<Category> findAllManagerCategories() {
	    EntityManager em = JpaUtil.getEntityManager();
	    try {
	     
	        TypedQuery<Category> query = em.createQuery(
	                "SELECT c FROM Category c JOIN FETCH c.user WHERE c.user.roleId = :roleId", Category.class);
	        query.setParameter("roleId", 2); 
	        return query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    } finally {
	        em.close();
	    }
	}
	
	public List<Category> findByUserId(int userId) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			TypedQuery<Category> query = em.createQuery(
					"SELECT c FROM Category c JOIN FETCH c.user WHERE c.user.id = :userId", Category.class);
			query.setParameter("userId", userId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}
	public void changeOwner(int categoryId, int newOwnerId) {
	    EntityManager em = JpaUtil.getEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        transaction.begin();
	        Category category = em.find(Category.class, categoryId);
	        User newOwner = em.find(User.class, newOwnerId);
	        if (category != null && newOwner != null) {
	            category.setUser(newOwner);
	            em.merge(category);
	        }
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        em.close();
	    }
	}
}