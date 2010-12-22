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
package xmldb.criteria.gof.function;

import xmldb.annotation.Attribute;
import xmldb.configuration.AnnotationScanner;
import xmldb.criteria.gof.XPathSintax;
import xmldb.util.AnnotationHelper;

/**
 *
 * @author GGabriele
 */
public abstract class Function implements XPathSintax{

    protected static final String PROPERTY = "<property>";
    protected static final String VALUE = "<value>";

    protected Class classe;
    protected String property;
    protected Object value;

    

    public Function(Class classe, String property, Object value) {
        this.classe = classe;
        this.property = property;
        this.value = value;
    }

    public Function() {
        //Solo funzioni AND OR Not
    }


    public String getXPath() {
        String query = getFunction();
        AnnotationScanner as = AnnotationHelper.get().get(classe);
        if (as.isAnnotatedWithAttribute(property)) {
            Attribute attribute = as.getAnnotation(property);
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query = query.replace(PROPERTY, "child::"+property.toLowerCase());
                } else {
                    query = query.replace(PROPERTY, "@"+property.toLowerCase());
                }
            }
            query = query.replace(VALUE, String.valueOf(value));
        }
        return query;
    }

    protected abstract String getFunction();
}
