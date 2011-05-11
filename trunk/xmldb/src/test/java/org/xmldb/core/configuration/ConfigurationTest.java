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
package org.xmldb.core.configuration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmldb.core.exceptions.XmlDBRuntimeException;
import static org.junit.Assert.*;
import org.xmldb.core.sessionfactory.SessionFactory;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class ConfigurationTest {

    public ConfigurationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Inizio Test --> ConfigurationTest");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Fine Test --> ConfigurationTest");
    }

    /**
     * Test of getSessionFactory method, of class Configuration.
     */
    @Test(expected=XmlDBRuntimeException.class)
    public void testGetSessionFactory() {
        System.out.println("test --> getSessionFactory");
        Configuration instance = new Configuration();
        SessionFactory result = instance.getSessionFactory();

        System.out.println("Ottenuto -->"+result);
        assertNull(result);
    }

    /**
     * Test of buildConfiguration method, of class Configuration.
     */
    @Test
    public void testBuildConfiguration() {
        System.out.println("test --> testBuildConfiguration");
        Configuration instance = new Configuration().buildConfiguration();
        SessionFactory result = instance.getSessionFactory();

        System.out.println("Ottenuto -->"+result);
        assertNotNull(result);
    }
}