/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.session;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.entity.Contatto;
import org.xml.entity.Telefono;
import org.xmldb.core.configuration.Configuration;
import static org.junit.Assert.*;
import org.xmldb.core.criteria.Criteria;
import org.xmldb.core.exceptions.TransactionNotActiveException;
import org.xmldb.core.exceptions.XmlDBRuntimeException;
import org.xmldb.core.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SessionTest {
 
    private static Session instance;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Inizio Test --> SessionTest");

        Configuration c = new Configuration().buildConfiguration();
        instance = c.getSessionFactory().openSession();

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Fine Test --> SessionTest");
    }

    /**
     * Test of merge method, of class Session.
     */
    @Test
    public void testMerge() {
        System.out.println("test --> merge");
        Contatto c = new Contatto();
        Contatto result = instance.merge(c);

        System.out.println("Aspettato --> "+c);
        System.out.println("Ottenuto  --> "+result);


        assertEquals(c, result);
        
    }


    @Test(expected=XmlDBRuntimeException.class)
    public void testMergeFailClassNonMapped() {
        System.out.println("test --> testMergeFailClassNonMapped");
        Telefono c = new Telefono();
        Telefono result = instance.merge(c);

        System.out.println("Aspettato --> "+c);
        System.out.println("Ottenuto  --> "+result);


        assertEquals(c, result);

    }

    /**
     * Test of load method, of class Session.
     */
    @Test
    public void testSaveTransactionActiveWithCommit() {
        System.out.println("test --> testSaveTransactionActiveWithCommit");
        Contatto c = new Contatto();
        c.setId(0);
        Transaction tx = instance.beginTransaction();
        c.setNome("testSaveTransactionActiveWithCommit");

        Object result = instance.merge(c);
        tx.commit();
        
    }


    @Test
    public void testSaveTransactionActiveWithRollback() {
        System.out.println("test --> testSaveTransactionActiveWithRollback");
        Contatto c = new Contatto();
        c.setId(0);
        Transaction tx = instance.beginTransaction();
        c.setNome("testSaveTransactionActiveWithRollback");

        instance.merge(c);
        tx.rollback();

    }

    @Test
    public void testLoadInsideTransactionActive() {
        System.out.println("test --> testLoadInsideTransactionActive");
        Contatto c = new Contatto();
        c.setId(0);
        Transaction tx = instance.beginTransaction();
        c.setNome("testLoadInsideTransactionActive");

        c = instance.merge(c);

        Contatto result = instance.load(Contatto.class, c.getId());

        tx.rollback();
        assertEquals(c.getNome(), result.getNome());
        
    }

    @Test(expected=TransactionNotActiveException.class)
    public void testGetCurrentTransactionWithoutBeginTransaction() {
        System.out.println("test -->testGetCurrentTransactionWithoutBeginTransaction");
        Transaction tx = instance.getCurrentTransaction();
    }


    /**
     * Test of getCurrentTransaction method, of class Session.
     */
    @Test
    public void testBeginTransaction() {
        System.out.println("test -->testBeginTransaction");

        Transaction tx1 = instance.beginTransaction();
        Transaction tx2 = instance.getCurrentTransaction();

        System.out.println("TX 1: --> "+tx1);
        System.out.println("TX 2: --> "+tx2);
        assertEquals(tx1, tx2);

        tx1.setRollbackOnly();

        tx2=instance.beginTransaction();

        System.out.println("TX 1: --> "+tx1);
        System.out.println("TX 2: --> "+tx2);

        assertNotSame(tx1, tx2);

        instance.getCurrentTransaction();
    }


    /**
     * Test of delete method, of class Session.
     */
    //@Test
    public void testDelete() {
        System.out.println("delete");
        Class<?> classe = null;
        Object id = null;
        instance.delete(classe, id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAll method, of class Session.
     */
   // @Test
    public void testFindAll() {
        System.out.println("findAll");
        Class<?> classe = null;
        List expResult = null;
        List result = instance.findAll(classe);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class Session.
     */
    //@Test
    public void testFind() {
        System.out.println("find");
        Criteria criteria = null;
        List expResult = null;
        List result = instance.find(criteria);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}