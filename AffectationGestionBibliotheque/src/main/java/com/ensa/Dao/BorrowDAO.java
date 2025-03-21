package com.ensa.Dao;

import com.ensa.entities.Document;
import com.ensa.entities.User;
import jakarta.persistence.*;

public class BorrowDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("affectation");

    public void borrowDocument(User user, Document document) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        user.getDocuments().add(document);
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public void returnDocument(User user, Document document) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        user.getDocuments().remove(document);
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }
}
