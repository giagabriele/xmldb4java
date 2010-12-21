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

import xmldb.annotation.Attribute;
import xmldb.configuration.AnnotationScanner;
import xmldb.util.AnnotationHelper;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class Like extends Function{

    protected static final String LIKE = "<child>[contains(<attribute><property>,'<value>')]<parent>";
    protected static final String CHILD = "<child>";
    protected static final String ATTRIBUTE = "<attribute>";
    protected static final String PROPERTY = "<property>";
    protected static final String VALUE = "<value>";
    protected static final String PARENT = "<parent>";

    protected String property;
    protected Object value;

    public Like(Class classe, String property, Object value) {
        super(classe);
        this.property = property;
        this.value = value;
    }

    @Override
    public String getQuery() {
        AnnotationScanner as = AnnotationHelper.get().get(classe);
        String query = LIKE;
        if (as.isAnnotatedWithAttribute(property)) {
            Attribute attribute = as.getAnnotation(property);
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query = query.replace(CHILD, "/"+property);
                    query = query.replace(ATTRIBUTE, "");
                    query = query.replace(PROPERTY, "text()");
                    query = query.replace(PARENT, "/parent::node()");
                } else {
                    query = query.replace(CHILD, "");
                    query = query.replace(ATTRIBUTE, "@");
                    query = query.replace(PROPERTY, property);
                    query = query.replace(PARENT, "");
                }
            }
            query = query.replace(VALUE, String.valueOf(value));
        }
        return query;
    }
}
