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
package org.xmldb.core.criteria.xpath;

import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.projections.Projection;



/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XPathQuery implements XPathSintax{

    protected static final String SELECT_ALL = "//";

    protected Condition condition;
    protected Projection projection;
    private PersistenceClass persistenceClass;

    public XPathQuery(PersistenceClass persistenceClass) {
        this.persistenceClass = persistenceClass;
        this.condition = new Condition();
    }

    public String getXPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(SELECT_ALL);
        
        sb.append(persistenceClass.getClassName());

        sb.append(condition.getXPath());

        String query = sb.toString();
        //projection
        if(projection!=null){
            projection.setQuery(query);
            query = projection.getXPath();
        }
        
        return query;
    }

    public boolean add(XPathSintax e) {
        return condition.add(e);
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }
}
