package com.rdtroyar.services;

import com.rdtroyar.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ServiceBookImpl implements ServiceBook{

    @Inject
    EntityManager em;


    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b").getResultList();
    }

    @Override
    public void insert(Book b) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(b);
            tx.commit();
        }catch (Exception ex){
            tx.rollback();
            //ex.printStackTrace();
        }
    }

    @Override
    public Book findById(Integer id) {
        return em.find(Book.class, id);
    }

    @Override
    public void update(Book b) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(b);
            tx.commit();
        }catch (Exception ex){
            tx.rollback();
        }
    }

    @Override
    public boolean delete(Integer id) {
        var tx = em.getTransaction();
        try {
            tx.begin();
            Book book = em.find(Book.class, id);
            if (book != null){
                em.remove(book);
                tx.commit();
                return true;
            }else {
                tx.rollback();
                return false;
            }
        }catch (Exception ex){
            tx.rollback();
            return false;
        }
    }
}
