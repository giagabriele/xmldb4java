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
package xmldb.criteria.gof.operator;

import xmldb.annotation.Attribute;
import xmldb.configuration.AnnotationScanner;
import xmldb.criteria.gof.XPathSintax;
import xmldb.util.AnnotationHelper;

/**
 *
 * @author GGabriele
 */
public abstract class Operator implements  XPathSintax{

    protected static final String OPERATION = "<property><operator><value>";

    protected static final String PROPERTY = "<property>";
    protected static final String VALUE = "<value>";
    protected static final String OPERATOR = "<operator>";

    protected String property;
    protected Object value;
    protected AnnotationScanner as;
    protected StringBuilder query;

    public Operator(Class classe, String property, Object value) {
        as = AnnotationHelper.get().get(classe);
        this.property = property;
        this.value = value;
        this.query = new StringBuilder();
    }

     @Override
    public String getXPath() {
        String sintax = OPERATION;

        if (as.isAnnotatedWithAttribute(property)) {
            Attribute attribute = as.getAnnotation(property);
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    sintax = sintax.replace(PROPERTY, "child::"+property.toLowerCase());
                } else {
                    sintax = sintax.replace(PROPERTY, "@"+property.toLowerCase());
                }
                if(value instanceof String){
                    sintax = sintax.replace(VALUE, "'"+String.valueOf(value)+"'");
                }else{
                    sintax = sintax.replace(VALUE, String.valueOf(value));
                }
            }
        }else{
            //Is Id type == attribute
            sintax = sintax.replace(PROPERTY, "@"+property.toLowerCase());
            sintax = sintax.replace(VALUE, String.valueOf(value));
        }

        sintax = sintax.replace(OPERATOR, getOperator());

        return sintax.toString();
    }

     protected abstract String getOperator();
}
