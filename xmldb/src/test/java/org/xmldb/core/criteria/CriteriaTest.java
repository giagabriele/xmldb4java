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
package org.xmldb.core.criteria;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.entity.Dettaglio;

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
    public void testCriteriaSequence() throws XPathExpressionException {
        System.out.println("Test --> testCriteriaSequence");
        
        Criteria instance = Criteria.createCriteria(Dettaglio.class);
        instance.add(Restrictions.idEq(1));
        instance.add(Restrictions.AND(Restrictions.not(Restrictions.propertyEq("number", 100)), Restrictions.propertyEq("cancelled", true)));
        
        validate(instance);
    }

    protected void validate(Criteria criteria) throws XPathExpressionException{
        System.out.println(criteria);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(criteria.getXPathQuery());
    }

   
}