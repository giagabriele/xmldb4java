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

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class Not extends Function{

    private XPathSintax xPathSintax;

    public Not(XPathSintax xPathSintax) {
        this.xPathSintax = xPathSintax;
    }

    @Override
    public String getXPath() {
        return "not("+xPathSintax.getXPath()+")";
    }

    @Override
    protected String getFunction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
