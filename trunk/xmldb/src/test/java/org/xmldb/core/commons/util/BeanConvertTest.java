/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.commons.util;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.entity.Contatto;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class BeanConvertTest {

    public BeanConvertTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getBeanUtils method, of class BeanConvert.
     */
    @Test
    public void testGetBeanUtils() throws Exception {
        System.out.println("getBeanUtils");

       BeanConvert.configure();

        Contatto contatto = new Contatto();

//        ConvertUtilsBean result = BeanConvert.getConvertUtils();
//        System.out.println(result.lookup(String[].class, String.class).convert(String.class, contatto.getDettagli()));
        System.out.println(BeanUtils.describe(contatto));
    }

}