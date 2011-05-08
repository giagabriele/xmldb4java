/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.trasformers;

import org.dom4j.Element;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.entity.Contatto;
import org.xmldb.core.annotation.bean.PersistenceClass;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class ObjectTrasformersTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Inizio Test --> ObjectTrasformersTest");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Fine Test --> ObjectTrasformersTest");
    }

    private static Element element;
    /**
     * Test of trasformElement method, of class ObjectTrasformers.
     */
    @Test
    public void testTrasformElement() {
        System.out.println("test --> trasformElement");
        Contatto c = new Contatto();
        
        ObjectTrasformers instance = new ObjectTrasformers(new PersistenceClass(Contatto.class));
        element = instance.trasformElement(c);
        System.out.println("Ottenuto --> \n"+element.asXML());
    }

    /**
     * Test of trasformModel method, of class ObjectTrasformers.
     */
    @Test
    public void testTrasformModel() {
        System.out.println("test --> trasformModel");
        ObjectTrasformers instance = new ObjectTrasformers(new PersistenceClass(Contatto.class));

        Object result = instance.trasformModel(element);
        System.out.println("Ottenuto --> \n"+result);
    }

}