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

/**
 *
 * @author GGabriele
 */
public class And extends Function{

    protected XPathQuery query1;
    protected XPathQuery query2;

    public And(Class classe, XPathQuery query1, XPathQuery query2) {
        super(classe);
        this.query1 = query1;
        this.query2 = query2;
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(query1.getQuery());
        sb.append(" and ");
        sb.append(query2.getQuery());
        return sb.toString();
    }
}
