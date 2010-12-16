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

import xmldb.configuration.AnnotationScanner;
import xmldb.util.AnnotationHelper;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class Criteria {

    private Class<? extends Object> classe;
    private StringBuilder query;
    protected Projection projection;

    /**
     * Costruttore privato
     * @param classe
     */
    private Criteria(Class<? extends Object> classe) {
        this.classe = classe;
        String select = classe.getSimpleName();
        try {
            select = AnnotationHelper.get().get(classe).getNameEntity();
        } catch (Exception e) {
            //v1 senza annotation
        }

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
        String select = classe.getSimpleName();
        try {
            select = AnnotationHelper.get().get(classe).getNameEntity();
        } catch (Exception e) {
            //v1 senza annotation
        }
        query.append("/").append(select);
        return this;
    }

    protected void addIdEq(Restrictions restrictions) {
        query.append("[@");
        try {
            AnnotationScanner as = AnnotationHelper.get().get(classe);
            query.append(as.getId().getName());
        } catch (Exception e) {
            query.append("id");
        }

        query.append("='");
        query.append(restrictions.getValue());
        query.append("']");
    }

    protected void addEq(Restrictions restrictions) {
        try {

            query.append("[@");
            query.append(restrictions.getProperty());
            query.append("='");
            query.append(restrictions.getValue());
            query.append("']");

        } catch (Exception e) {
        }
    }

    protected void addGT(Restrictions restrictions) {
        query.append("[@");
        query.append(restrictions.getProperty());
        query.append(">");
        query.append(restrictions.getValue());
        query.append("]");
    }

    protected void addLT(Restrictions restrictions) {
        query.append("[@");
        query.append(restrictions.getProperty());
        query.append("<");
        query.append(restrictions.getValue());
        query.append("]");
    }

    protected void addLike(Restrictions restrictions) {
        query.append("[contains(@");
        query.append(restrictions.getProperty());
        query.append(",'");
        query.append(restrictions.getValue());
        query.append("')]");
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
