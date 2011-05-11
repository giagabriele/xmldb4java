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


import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.entity.Dettaglio;
import org.xmldb.core.configuration.Configuration;
import org.xmldb.core.session.Session;
import org.xmldb.core.transaction.Transaction;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class InserimentoMassivoTest {

    protected static final int N_ELEMENT = 100;
    protected static final int timeUnit = 100;

    
    private static Session instance;
    private List<Dettaglio> dettagli = new ArrayList<Dettaglio>();
    private long time = 0;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("Inizio Test --> InserimentoMassivoTest");

        Configuration c = new Configuration("memory:test-inserimentoMassivo-xmldb.xml");
        instance = c.getSessionFactory().openSession();

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        instance.close();
        System.out.println("Fine Test --> InserimentoMassivoTest");
    }

    @Before
    public void beforeInsert(){
        for(int i=0;i<N_ELEMENT;i++){
            Dettaglio d = new Dettaglio();
            d.setDettaglio("dettaglio "+i);
            dettagli.add(d);
        }
        System.out.println("test -->testInsert "+N_ELEMENT);
        time = System.currentTimeMillis();
    }
    
    @Test(timeout=N_ELEMENT*timeUnit)
    public void testInsert(){
        Transaction tx = instance.beginTransaction();
        for(Dettaglio d:dettagli){
            instance.merge(d);
        }
        tx.commit();
    }

    @After
    public void afterInsert(){
        time = System.currentTimeMillis()-time;
        System.out.println("Time testInsert:"+time/1000+" s");
    }
}