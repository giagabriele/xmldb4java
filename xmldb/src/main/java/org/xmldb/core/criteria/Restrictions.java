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
package org.xmldb.core.criteria;

import org.xmldb.core.criteria.xpath.XPathSintax;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.xmldb.core.ErrorCode;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.criteria.xpath.XPathQueryFactory;
import org.xmldb.core.exceptions.XmlDBRuntimeException;


/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class Restrictions {

    public enum Operation {
        EQ, OR, AND, LIKE, GT,GT_EQ, LT,LT_EQ, ID_EQ, START_WITH,BETWEEN,NOT
    }
    
    private String property;
    private Object value;
    //per operazioni between di date e di numeri
    private long start;
    private long end;
    private boolean startIncluded;
    private boolean endIncluded;
    //
    private Operation operation;
    private Restrictions r1;
    private Restrictions r2;
    protected PersistenceClass persistenceClass;

    

    private Restrictions(String property, Object value, Operation operation) {
        this.operation = operation;
        this.property = property;
        this.value = value;
    }

    private Restrictions(Restrictions r1, Restrictions r2, Operation operation) {
        this.operation = operation;
        this.r1 = r1;
        this.r2 = r2;
    }

    private Restrictions(String property, long start, long end, boolean startIncluded, boolean endIncluded, Operation operation) {
        this.property = property;
        this.start = start;
        this.end = end;
        this.startIncluded = startIncluded;
        this.endIncluded = endIncluded;
        this.operation = operation;
    }

    public XPathSintax getPathSintax() {

        switch (operation) {
            case EQ:
                return XPathQueryFactory.createEq(persistenceClass, property, value);
            case AND:
                r1.persistenceClass = persistenceClass;
                r2.persistenceClass = persistenceClass;
                return XPathQueryFactory.createAnd(r1.getPathSintax(), r2.getPathSintax());
            case OR:
                r1.persistenceClass = persistenceClass;
                r2.persistenceClass = persistenceClass;
                return XPathQueryFactory.createOR(r1.getPathSintax(), r2.getPathSintax());
            case LIKE:
                return XPathQueryFactory.createLike(persistenceClass, property, value);
            case GT:
                return XPathQueryFactory.createGt(persistenceClass, property, value);
            case GT_EQ:
                return XPathQueryFactory.createGtEq(persistenceClass, property, value);
            case LT:
                return XPathQueryFactory.createLt(persistenceClass, property, value);
            case LT_EQ:
                return XPathQueryFactory.createLtEq(persistenceClass, property, value);
            case ID_EQ:
                return XPathQueryFactory.createEq(persistenceClass, persistenceClass.getPkField().getFieldName(), value);
            case START_WITH:
                return XPathQueryFactory.createStartWith(persistenceClass, property, value);
            case BETWEEN:
                return XPathQueryFactory.createBetween(persistenceClass, property, start, end, startIncluded, endIncluded);
            case NOT:
                r1.persistenceClass = persistenceClass;
                return XPathQueryFactory.not(r1.getPathSintax());
            default:
                break;
        }
        return null;
    }

    public static Restrictions AND(Restrictions r1, Restrictions r2) {
        return new Restrictions(r1, r2, Operation.AND);
    }

    public static Restrictions idEq(Object value) {
        return new Restrictions(null, value, Operation.ID_EQ);
    }

    public static Restrictions propertyEq(String property, Object value) {
        return new Restrictions(property, value, Operation.EQ);
    }
    /**
     * Greater than
     * @param property
     * @param value
     * @return restrictions
     */
    public static Restrictions gt(String property, Object value) {
        return new Restrictions(property, value, Operation.GT);
    }
    /**
     * Greater than or equal to
     * @param property
     * @param value
     * @return restrictions
     */
    public static Restrictions gtEq(String property, Object value) {
        return new Restrictions(property, value, Operation.GT_EQ);
    }
    /**
     * Less than
     * @param property
     * @param value
     * @return restrictions
     */
    public static Restrictions lt(String property, Object value) {
        return new Restrictions(property, value, Operation.LT);
    }
    /**
     * Less than or equal to
     * @param property
     * @param value
     * @return restrictions
     */
    public static Restrictions ltEq(String property, Object value) {
        return new Restrictions(property, value, Operation.LT_EQ);
    }

    public static Restrictions like(String property, Object value) {
        return new Restrictions(property, value, Operation.LIKE);
    }

    public static Restrictions OR(Restrictions r1, Restrictions r2) {
        return new Restrictions(r1, r2, Operation.OR);
    }

    public static Restrictions startWith(String property, Object value) {
        return new Restrictions(property, String.valueOf(value), Operation.START_WITH);
    }
    /**
     * Betwheen estremi inclusi
     * @param property
     * @param start
     * @param end
     * @return restriction
     */
    public static Restrictions between(String property,long start,long end){
        return between(property, start, end, true, true);
    }
    /**
     * Betwheen estremi inclusi
     * @param property
     * @param pattern esempio dd/MM/yyyy
     * @param dateStart data in formato string
     * @param dateEnd data in formato string
     * @return restrinctions
     */
    public static Restrictions between(String property,String pattern,String dateStart,String dateEnd){
        return between(property, pattern, dateStart, dateEnd,true,true);
    }
    /**
     * Betwheen date con estremi
     * @param property
     * @param pattern esempio dd/MM/yyyy
     * @param dateStart data in formato string
     * @param dateEnd data in formato string
     * @param includeStart
     * @param includeEnd
     * @return restrictions
     */
    public static Restrictions between(String property,String pattern,String dateStart,String dateEnd,boolean includeStart,boolean includeEnd){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try{
            Date d1 = simpleDateFormat.parse(dateStart);
            Date d2 = simpleDateFormat.parse(dateEnd);

            return between(property, d1.getTime(), d2.getTime(), includeStart, includeEnd);
        }catch(ParseException e){
            throw new XmlDBRuntimeException(ErrorCode.ERRORE_XPATH_FUNCTION_BEETWEN, pattern,dateStart,dateEnd);
        }
    }
    /**
     * Betwheen
     * @param property
     * @param start
     * @param end
     * @param includeStart
     * @param includeEnd
     * @return restrictions
     */
    public static Restrictions between(String property,long start,long end,boolean includeStart,boolean includeEnd){
        return new Restrictions(property, start, end, includeStart, includeEnd, Restrictions.Operation.BETWEEN);
    }
    /**
     * Restrinctions di negazione
     * @param restrictions
     * @return restrictions
     */
    public static Restrictions not(Restrictions restrictions){
        return new Restrictions(restrictions, restrictions, Restrictions.Operation.NOT);
    }
}
