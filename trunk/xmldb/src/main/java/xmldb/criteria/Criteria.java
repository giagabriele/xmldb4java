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
package xmldb.criteria;

import org.apache.log4j.Logger;
import xmldb.criteria.gof.XPathQuery;
import xmldb.criteria.gof.XPathSintax;
import xmldb.criteria.projections.Projection;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class Criteria {

    protected static final Logger logger = Logger.getLogger(Criteria.class);
    private Class<? extends Object> classe;
    protected XPathQuery queryXPath;

    /**
     * Costruttore privato
     * @param classe
     */
    private Criteria(Class<? extends Object> classe) {
        this.classe = classe;
        this.queryXPath = new XPathQuery(classe);
    }

    public Criteria add(Restrictions restrictions) {
        if(restrictions!=null){
            restrictions.classe = classe;
            XPathSintax s = restrictions.getPathSintax();//XPathQueryFactory.create(classe, restrictions);
            queryXPath.add(s);
        }
        return this;
    }

    public Class<?> getClasse() {
        return classe;
    }

    public void setProjection(Projection projection) {
        if(projection !=null){
            projection.setClasse(classe);
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

    @Override
    public String toString() {
        return "Criteria Xpath: " + getXPathQuery();
    }

    public static Criteria createCriteria(Class<? extends Object> classe) {
        return new Criteria(classe);
    }
}
