/*
 * Copyright 2011 Giacomo Stefano Gabriele
 *
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