package vn.week3.dao;
import vn.week3.model.Category;
import vn.week3.model.User;
import vn.week3.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class UserDAO {
	public User findByUsername(String username) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public void update(User user) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive())
				transaction.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<User> findAll() {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}

	public void delete(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			User user = em.find(User.class, id);
			if (user != null) {
				em.remove(user);
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

	public User findById(int id) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			return em.find(User.class, id);
		} finally {
			em.close();
		}
	}

	public void registerUser(User user) {
		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			String plainPassword = user.getPassword();
			user.setPassword(plainPassword);

			user.setRoleId(1);
			user.setCreatedDate(new Date(System.currentTimeMillis()));

			em.persist(user);

			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			throw e;
		} finally {
			em.close();
		}
	}

	public boolean usernameExists(String username) {
		return existsByField("username", username);
	}

	public boolean emailExists(String email) {
		return existsByField("email", email);
	}

	private boolean existsByField(String fieldName, String value) {
		EntityManager em = JpaUtil.getEntityManager();
		try {
			String jpql = "SELECT COUNT(u) FROM User u WHERE u." + fieldName + " = :value";
			TypedQuery<Long> query = em.createQuery(jpql, Long.class);
			query.setParameter("value", value);
			return query.getSingleResult() > 0;
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