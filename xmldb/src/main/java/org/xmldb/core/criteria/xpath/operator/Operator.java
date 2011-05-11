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
package org.xmldb.core.criteria.xpath.operator;

import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.xpath.XPathSintax;

/**
 *
 * @author GGabriele
 */
public abstract class Operator implements XPathSintax {

    protected static final String OPERATION = "<property><operator><value>";
    protected static final String PROPERTY = "<property>";
    protected static final String VALUE = "<value>";
    protected static final String OPERATOR = "<operator>";
    protected String property;
    protected Object value;
    protected PersistenceClass persistenceClass;
    protected StringBuilder query;

    public Operator(PersistenceClass persistenceClass, String property, Object value) {
        this.persistenceClass = persistenceClass;
        this.property = property;
        this.value = value;
        this.query = new StringBuilder();
    }

    @Override
    public String getXPath() {
        String sintax = OPERATION;

        if (persistenceClass.getPkField().getFieldName().equals(property)) {
            sintax = sintax.replace(PROPERTY, "@" + property);
        } else {
            sintax = sintax.replace(PROPERTY, "child::" + property);
        }

        if (value instanceof String) {
            sintax = sintax.replace(VALUE, "'" + String.valueOf(value) + "'");
        } else {
            sintax = sintax.replace(VALUE, String.valueOf(value));
        }


        sintax = sintax.replace(OPERATOR, getOperator());

        return sintax.toString();
    }

    protected abstract String getOperator();
}
