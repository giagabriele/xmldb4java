/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.criteria;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xmldb.core.type.Sequence;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class CriteriaTest {


    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Inizio Test --> CriteriaTest");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Fine Test --> CriteriaTest");
    }

    /**
     * Test of add method, of class Criteria.
     */
    @Test
    public void testCriteriaSequence() {
        System.out.println("Test --> testCriteriaSequence");
        
        Criteria instance = Criteria.createCriteria(Sequence.class);
        instance.add(Restrictions.idEq(1));
        
        String expResult = "//Sequence[@id=1]";
        String result = instance.getXPathQuery();

        System.out.println("Atteso   --> "+expResult);
        System.out.println("Ottenuto --> "+result);

        assertEquals(expResult, result);
    }

   
}