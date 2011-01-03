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

import xmldb.criteria.gof.function.And;
import xmldb.criteria.gof.function.Betwheen;
import xmldb.criteria.gof.operator.Gt;
import xmldb.criteria.gof.operator.Eq;
import xmldb.criteria.gof.function.Like;
import xmldb.criteria.gof.function.OR;
import xmldb.criteria.gof.function.StartWith;
import xmldb.criteria.gof.operator.GtEq;
import xmldb.criteria.gof.operator.Lt;
import xmldb.criteria.gof.operator.LtEq;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XPathQueryFactory {

    public static XPathSintax createLike(Class clazz, String property, Object value) {
       return new Like(clazz, property, value);
    }

    public static XPathSintax createEq(Class clazz, String property, Object value) {
       return new Eq(clazz, property, value);
    }

    public static XPathSintax createGt(Class clazz, String property, Object value) {
        return new Gt(clazz, property, value);
    }

    public static XPathSintax createGtEq(Class clazz, String property, Object value) {
        return new GtEq(clazz, property, value);
    }

    public static XPathSintax createLt(Class clazz, String property, Object value) {
        return new Lt(clazz, property, value);
    }

    public static XPathSintax createLtEq(Class clazz, String property, Object value) {
        return new LtEq(clazz, property, value);
    }

    public static XPathSintax createStartWith(Class clazz, String property, Object value) {
        return new StartWith(clazz,property,value);
    }

    public static XPathSintax createAnd(XPathSintax q1, XPathSintax q2) {
        return new And(q1,q2);
    }

    public static XPathSintax createOR(XPathSintax q1, XPathSintax q2) {
        return new OR(q1,q2);
    }

    public static XPathSintax createBetween(Class classe,String property,long start, long end, boolean incudeStart, boolean incudeEnd) {
       return new Betwheen(classe, property, start, end, incudeStart, incudeEnd);
    }
}
