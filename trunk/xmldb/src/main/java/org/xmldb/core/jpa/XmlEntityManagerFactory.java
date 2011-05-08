/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.jpa;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmlEntityManagerFactory implements EntityManagerFactory{

    public EntityManager createEntityManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EntityManager createEntityManager(Map map) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
