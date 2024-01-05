package com.rdtroyar.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class Jpaconfig {

    private EntityManagerFactory emf;

    @PostConstruct
    public void init(){
        System.out.println("init");
        emf = Persistence.createEntityManagerFactory("pu-evaluacion");
    }

    @Produces
    EntityManager em (){
        System.out.println("***em***");
        return emf.createEntityManager();
    }
}
