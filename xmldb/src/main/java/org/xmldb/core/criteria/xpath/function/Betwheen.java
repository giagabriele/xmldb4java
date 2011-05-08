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
package org.xmldb.core.criteria.xpath.function;

import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.xpath.XPathQueryFactory;
import org.xmldb.core.criteria.xpath.XPathSintax;


/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class Betwheen extends Function{

    protected long start;
    protected long end;
    protected boolean incudeStart;
    protected boolean incudeEnd;

    public Betwheen(PersistenceClass persistenceClass,String property,long start, long end, boolean incudeStart, boolean incudeEnd) {
        this.start = start;
        this.end = end;
        this.incudeStart = incudeStart;
        this.incudeEnd = incudeEnd;
        super.property = property;
        super.persistenceClass = persistenceClass;
    }

    

    @Override
    public String getXPath() {
        
        XPathSintax xps1 = null;
        XPathSintax xps2 = null;
        //Inizio
        if(incudeStart){
            xps1 = XPathQueryFactory.createGtEq(persistenceClass, property, start);
        }else{
            xps1 = XPathQueryFactory.createGt(persistenceClass, property, start);
        }
        //FIne
        if(incudeEnd){
            xps2 = XPathQueryFactory.createLtEq(persistenceClass, property, end);
        }else{
            xps2 = XPathQueryFactory.createLt(persistenceClass, property, end);
        }

        XPathSintax xps = XPathQueryFactory.createOR(xps1, xps2);

        return xps.getXPath();
    }

    @Override
    protected String getFunction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
