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
package xmldb.criteria.gof;

import xmldb.configuration.AnnotationScanner;
import xmldb.criteria.projections.Projection;
import xmldb.util.AnnotationHelper;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XPathQuery implements XPathSintax{

    protected static final String SELECT_ALL = "//";

    protected Class classe;
    protected Condition condition;
    protected Projection projection;

    public XPathQuery(Class classe) {
        this.classe = classe;
        this.condition = new Condition();
    }

    public String getXPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(SELECT_ALL);
        AnnotationScanner as = AnnotationHelper.get().get(classe);
        sb.append(as.getNameEntity());

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
