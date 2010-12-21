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

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class Gt extends Operator{

    public Gt(Class classe, String property, Object value) {
        super(classe, property, value);
    }

    @Override
    public String getQuery() {
        if (as.isAnnotatedWithAttribute(property)) {
            Attribute attribute = as.getAnnotation(property);
            if (attribute != null) {
                if (attribute.tipo().equals(Attribute.TIPO.ELEMENT)) {
                    query.append("/");
                    query.append(property);
                    query.append("[text()>");
                    query.append(value);
                    query.append("]/parent::node()");
                } else {
                    query.append("[@");
                    query.append(property);
                    query.append(">");
                    query.append(value);
                    query.append("]");
                }
            }
        }
        return query.toString();
    }

}
