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

import xmldb.criteria.gof.XPathSintax;
import xmldb.exception.XmlDBException;

/**
 *
 * @author GGabriele
 */
public class OR extends Function {

    protected XPathSintax q1;
    protected XPathSintax q2;

    public OR(XPathSintax q1, XPathSintax q2) {
        if(q1==null || q2==null){
            throw new XmlDBException("OR Function:una delle due sintassi xpath è null");
        }
        this.q1 = q1;
        this.q2 = q2;
    }

    @Override
    public String getXPath() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(q1.getXPath()).append(" or ").append(q2.getXPath()).append(")");
        return sb.toString();
    }

    @Override
    protected String getFunction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
