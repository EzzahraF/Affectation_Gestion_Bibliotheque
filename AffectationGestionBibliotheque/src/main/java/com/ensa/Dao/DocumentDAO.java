package com.ensa.Dao;

import com.ensa.entities.Document;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class DocumentDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("affectation");

    public Document findDocumentById(Long id) {
        EntityManager em = emf.createEntityManager();
        Document document = em.find(Document.class, id);
        em.close();
        return document;
    }

    public List<Document> findAllDocuments() {
        EntityManager em = emf.createEntityManager();
        List<Document> documents = em.createQuery("SELECT d FROM Document d", Document.class).getResultList();
        em.close();
        return documents;
    }

    public void addDocument(Document document) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(document);
        em.getTransaction().commit();
        em.close();
    }

    public boolean deleteDocument(Long id) {
        EntityManager em = emf.createEntityManager();
        Document document = em.find(Document.class, id);

        if (document != null) {
            em.getTransaction().begin();
            em.remove(document);
            em.getTransaction().commit();
            em.close();
            return true;
        }

        em.close();
        return false;
    }
}
