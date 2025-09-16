package vn.week3.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import vn.week3.model.Video;
import vn.week3.util.JpaUtil;
import vn.week3.model.User;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class VideoDAO {

	  public void add(Video video, int userId) {
	        EntityManager em = JpaUtil.getEntityManager();
	        EntityTransaction transaction = em.getTransaction();
	        try {
	            transaction.begin();

	            User managedUser = em.find(User.class, userId);
	            video.setUser(managedUser);
	            em.persist(video);
	            
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction.isActive()) transaction.rollback();
	            e.printStackTrace(); 
	            throw e; 
	        } finally {
	            em.close();
	        }
	    }


    public void update(Video video) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(video);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
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
            Video video = em.find(Video.class, id);
            if (video != null) {
                if (video.getPoster() != null && !video.getPoster().isEmpty()) {
                    File fileToDelete = new File(uploadPath + File.separator + video.getPoster());
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                }
                em.remove(video);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Video findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery("SELECT v FROM Video v JOIN FETCH v.user WHERE v.id = :id", Video.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Video> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Video> query = em.createQuery("SELECT v FROM Video v JOIN FETCH v.user ORDER BY v.id DESC", Video.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}