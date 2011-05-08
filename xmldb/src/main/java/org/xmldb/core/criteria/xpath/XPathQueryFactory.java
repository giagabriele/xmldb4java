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
package org.xmldb.core.criteria.xpath;

import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.xpath.function.And;
import org.xmldb.core.criteria.xpath.function.Betwheen;
import org.xmldb.core.criteria.xpath.function.Like;
import org.xmldb.core.criteria.xpath.function.Not;
import org.xmldb.core.criteria.xpath.function.OR;
import org.xmldb.core.criteria.xpath.function.StartWith;
import org.xmldb.core.criteria.xpath.operator.Eq;
import org.xmldb.core.criteria.xpath.operator.Gt;
import org.xmldb.core.criteria.xpath.operator.GtEq;
import org.xmldb.core.criteria.xpath.operator.Lt;
import org.xmldb.core.criteria.xpath.operator.LtEq;


/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XPathQueryFactory {

    public static XPathSintax createLike(PersistenceClass persistenceClass, String property, Object value) {
       return new Like(persistenceClass, property, value);
    }

    public static XPathSintax createEq(PersistenceClass persistenceClass, String property, Object value) {
       return new Eq(persistenceClass, property, value);
    }

    public static XPathSintax createGt(PersistenceClass persistenceClass, String property, Object value) {
        return new Gt(persistenceClass, property, value);
    }

    public static XPathSintax createGtEq(PersistenceClass persistenceClass, String property, Object value) {
        return new GtEq(persistenceClass, property, value);
    }

    public static XPathSintax createLt(PersistenceClass persistenceClass, String property, Object value) {
        return new Lt(persistenceClass, property, value);
    }

    public static XPathSintax createLtEq(PersistenceClass persistenceClass, String property, Object value) {
        return new LtEq(persistenceClass, property, value);
    }

    public static XPathSintax createStartWith(PersistenceClass persistenceClass, String property, Object value) {
        return new StartWith(persistenceClass,property,value);
    }

    public static XPathSintax createAnd(XPathSintax q1, XPathSintax q2) {
        return new And(q1,q2);
    }

    public static XPathSintax createOR(XPathSintax q1, XPathSintax q2) {
        return new OR(q1,q2);
    }

    public static XPathSintax createBetween(PersistenceClass persistenceClass,String property,long start, long end, boolean incudeStart, boolean incudeEnd) {
       return new Betwheen(persistenceClass, property, start, end, incudeStart, incudeEnd);
    }

    public static XPathSintax not(XPathSintax xPathSintax){
        return new Not(xPathSintax);
    }
}
