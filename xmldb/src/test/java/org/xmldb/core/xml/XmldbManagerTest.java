/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xmldb.core.xml;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.entity.Contatto;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.exceptions.ElementNotFoundException;
import org.xmldb.core.exceptions.XmlDBRuntimeException;
import org.xmldb.core.xml.manager.XmldbManager;
import org.xmldb.core.xml.manager.XmldbManagerFactory;
import static org.junit.Assert.*;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmldbManagerTest {

    private static XmldbManager instance ;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Inizio Test --> XmldbManagerTest");

        instance = XmldbManagerFactory.createXmldbManager("memory:xmldb-test.xml");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Fine Test --> XmldbManagerTest");
        
        System.out.println("-------------------------------------------");
        System.out.println(instance.toString());
    }


    @Test(expected=XmlDBRuntimeException.class)
    public void inizializeXmldbManagerFailure() throws Exception {
        System.out.println("Test --> inizializeXmldbManagerFailure");

        XmldbManagerFactory.createXmldbManager("xxxxx.xml");
    }

    /**
     * Test of merge method, of class FileSystemXmldbManagerImpl.
     */
    @Test
    public void testMerge() {
        System.out.println("test --> merge");
        Contatto c = new Contatto();
        Contatto result = instance.merge(new PersistenceClass(c));

        assertEquals(c, result);
        
    }

    /**
     * Test of load method, of class FileSystemXmldbManagerImpl.
     */
    @Test
    public void testLoad() {
        System.out.println("test --> load");

        Contatto result = instance.load(new PersistenceClass(Contatto.class), 1);

        assertNotNull(result);
        assertTrue(result.getId()==1);
    }

    /**
     * Test of remove method, of class FileSystemXmldbManagerImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("test --> remove");
        instance.remove(new PersistenceClass(Contatto.class), 1);
    }

    @Test(expected=ElementNotFoundException.class)
    public void testLoadNonExist() {
        System.out.println("test --> testLoadNonExist");

        Contatto result = instance.load(new PersistenceClass(Contatto.class), 1);
    }

    
    

//    /**
//     * Test of commit method, of class FileSystemXmldbManagerImpl.
//     */
//    @Test
//    public void testCommit() {
//        System.out.println("commit");
//        instance.commit();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rollback method, of class FileSystemXmldbManagerImpl.
//     */
//    @Test
//    public void testRollback() {
//        System.out.println("rollback");
//        FileSystemXmldbManagerImpl instance = null;
//        instance.rollback();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}