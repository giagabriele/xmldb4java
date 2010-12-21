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
import xmldb.annotation.Attribute;
import xmldb.configuration.AnnotationScanner;
import xmldb.util.AnnotationHelper;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class Criteria {

    protected static final Logger logger = Logger.getLogger(Criteria.class);
    private Class<? extends Object> classe;
    private StringBuilder query;
    protected Projection projection;
    protected AnnotationScanner as = null;

    /**
     * Costruttore privato
     * @param classe
     */
    private Criteria(Class<? extends Object> classe) {
        this.classe = classe;
        this.as = AnnotationHelper.get().get(classe);

        String select = as.getNameEntity();
        query = new StringBuilder();
        query.append("//").append(select);
    }

    public Criteria add(Restrictions restrictions) {
        switch (restrictions.getOperation()) {
            case EQ:
                addEq(restrictions);
                break;
            case AND:
                add(restrictions.getR1());
                add(restrictions.getR2());
                break;
            case OR:
                //TODO
                break;
            case LIKE:
                addLike(restrictions);
                break;
            case GT:
                addGT(restrictions);
                break;
            case LT:
                addLT(restrictions);
                break;
            case ID_EQ:
                addIdEq(restrictions);
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * Scende di livello nell'xml<br>
     * @param classe
     * @return criteria
     */
    Criteria create(Class<? extends Object> classe) {
        this.classe = classe;
        String select = AnnotationHelper.get().get(classe).getNameEntity();

        query.append("/").append(select);
        return this;
    }

    protected void addIdEq(Restrictions restrictions) {
        query.append("[@");
        query.append(as.getId().getName());

        query.append("='");
        query.append(restrictions.getValue());
        query.append("']");
    }

    protected void addEq(Restrictions restrictions) {
        if (as.isAnnotatedWithAttribute(restrictions.getProperty())) {
            Attribute attribute = as.getAnnotation(restrictions.getProperty());
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query.append("/");
                    query.append(restrictions.getProperty());
                    query.append("[text()='");
                    query.append(restrictions.getValue());
                    query.append("']/parent::node()");
                    return;
                } else {
                    query.append("[@");
                    query.append(restrictions.getProperty());
                    query.append("='");
                    query.append(restrictions.getValue());
                    query.append("']");
                }
            }
        }

    }

    protected void addOR(Restrictions r1, Restrictions r2) {
    }

    protected void addGT(Restrictions restrictions) {
        if (as.isAnnotatedWithAttribute(restrictions.getProperty())) {
            Attribute attribute = as.getAnnotation(restrictions.getProperty());
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query.append("/");
                    query.append(restrictions.getProperty());
                    query.append("[text()>");
                    query.append(restrictions.getValue());
                    query.append("]/parent::node()");
                    return;
                } else {
                    query.append("[@");
                    query.append(restrictions.getProperty());
                    query.append(">");
                    query.append(restrictions.getValue());
                    query.append("]");
                }
            }
        }
    }

    protected void addLT(Restrictions restrictions) {
        if (as.isAnnotatedWithAttribute(restrictions.getProperty())) {
            Attribute attribute = as.getAnnotation(restrictions.getProperty());
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query.append("/");
                    query.append(restrictions.getProperty());
                    query.append("[text()<");
                    query.append(restrictions.getValue());
                    query.append("]/parent::node()");
                    return;
                } else {
                    query.append("[@");
                    query.append(restrictions.getProperty());
                    query.append("<");
                    query.append(restrictions.getValue());
                    query.append("]");
                }
            }
        }

    }

    protected void addLike(Restrictions restrictions) {
        if (as.isAnnotatedWithAttribute(restrictions.getProperty())) {
            Attribute attribute = as.getAnnotation(restrictions.getProperty());
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query.append("/");
                    query.append(restrictions.getProperty());
                    query.append("[contains(text(),'");
                    query.append(restrictions.getValue());
                    query.append("')]/parent::node()");
                    return;
                } else {
                    query.append("[contains(@");
                    query.append(restrictions.getProperty());
                    query.append(",'");
                    query.append(restrictions.getValue());
                    query.append("')]");
                }
            }
        }
    }

    public Class<? extends Object> getClasse() {
        return classe;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /**
     * Ritorna la xPath equivalente al criterio
     * @return xpath
     */
    public String getXPathQuery() {
        if (projection != null) {
            switch (projection.getType()) {
                case ROW_COUNT:
                    return "count(" + query.toString() + ")";
            }
        }
        return query.toString();
    }

    @Override
    public String toString() {
        return "Criteria Xpath: " + getXPathQuery();
    }

    public static Criteria createCriteria(Class<? extends Object> classe) {
        return new Criteria(classe);
    }
}
