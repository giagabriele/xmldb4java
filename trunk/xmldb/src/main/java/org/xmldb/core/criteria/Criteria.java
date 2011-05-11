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

import org.xmldb.core.criteria.xpath.XPathSintax;
import org.apache.log4j.Logger;
import org.xmldb.core.annotation.PersistenceClassManager;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.projections.Projection;
import org.xmldb.core.criteria.xpath.XPathQuery;



/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class Criteria {

    protected static final Logger logger = Logger.getLogger(Criteria.class);
    private PersistenceClass persistenceClass;
    protected XPathQuery queryXPath;

    /**
     * Costruttore privato
     * @param classe
     */
    private Criteria(PersistenceClass persistenceClass) {
        this.persistenceClass = persistenceClass;
        this.queryXPath = new XPathQuery(persistenceClass);
    }

    public Criteria add(Restrictions restrictions) {
        if(restrictions!=null){
            restrictions.setPersistenceClass(persistenceClass);
            XPathSintax s = restrictions.getPathSintax();
            queryXPath.add(s);
        }
        return this;
    }

    public Class<?> getClasse() {
        return persistenceClass.getClazz();
    }

    public void setProjection(Projection projection) {
        if(projection !=null){
            projection.setClasse(persistenceClass.getClazz());
            this.queryXPath.setProjection(projection);
        }
    }

    /**
     * Ritorna la xPath equivalente al criterio
     * @return xpath
     */
    public String getXPathQuery() {
        String query = queryXPath.getXPath();
        return query;
    }

    public XPathQuery getQueryXPath() {
        return queryXPath;
    }

    public PersistenceClass getPersistenceClass(){
        return persistenceClass;
    }

    @Override
    public String toString() {
        return "Criteria Xpath: " + getXPathQuery();
    }

    public static Criteria createCriteria(Class<? extends Object> classe) {
        return new Criteria(getPersistenceClass(classe));
    }

    private static PersistenceClass getPersistenceClass(Class clazz){
        return PersistenceClassManager.get(clazz);
    }
}
