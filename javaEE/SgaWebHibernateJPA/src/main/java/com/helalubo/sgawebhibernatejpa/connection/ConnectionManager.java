/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.sgawebhibernatejpa.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Helalubo
 */
public abstract class ConnectionManager {
    protected static EntityManager em;
    private static EntityManagerFactory emf;
    private static final String PU = "SgaSource";
    
    
    public ConnectionManager(){
        if(emf == null){
            emf = Persistence. createEntityManagerFactory(PU);
        }
    }
    
    protected EntityManager getEntityManager(){
        if(em == null){
            em = emf.createEntityManager();
        }
        return em;
    }
}
