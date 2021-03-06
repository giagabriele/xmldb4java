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
package org.xmldb.core.criteria.projections;

import org.xmldb.core.criteria.xpath.XPathSintax;



/**
 * 
 * @author Giacomo Stefano Gabriele
 *
 */
public abstract class Projection implements XPathSintax {

    protected String query;
    protected Class classe;

    public void setQuery(String query) {
        this.query = query;
    }

    public static Projection rowCount(){
        return new RowCount();
    }

    public static Projection projectionList(String property){
        return new ProjectionList(property);
    }

    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }
    
}
