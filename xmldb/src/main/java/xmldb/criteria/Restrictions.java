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
package xmldb.criteria;

/**
 * 
 * @author Giacomo Stefano Gabriele
 * 
 */
public class Restrictions {

    private String property;
    private String value;
    private Operation operation;
    private Restrictions r1;
    private Restrictions r2;

    private Restrictions(String property, String value, Operation operation) {
        this.operation = operation;
        this.property = property;
        this.value = value;
    }

    private Restrictions(Restrictions r1, Restrictions r2, Operation operation) {
        this.operation = operation;
        this.r1 = r1;
        this.r2 = r2;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public Restrictions getR1() {
        return r1;
    }

    public Restrictions getR2() {
        return r2;
    }

    public static Restrictions AND(Restrictions r1, Restrictions r2) {
        return new Restrictions(r1, r2, Operation.AND);
    }

    public static Restrictions idEq(Object value) {
        return new Restrictions(null, String.valueOf(value), Operation.ID_EQ);
    }

    public static Restrictions propertyEq(String property, Object value) {
        return new Restrictions(property, String.valueOf(value), Operation.EQ);
    }

    public static Restrictions gt(String property, Object value) {
        return new Restrictions(property, String.valueOf(value), Operation.GT);
    }

    public static Restrictions lt(String property, Object value) {
        return new Restrictions(property, String.valueOf(value), Operation.LT);
    }

    public static Restrictions like(String property, Object value) {
        return new Restrictions(property, String.valueOf(value), Operation.LIKE);
    }

    public static Restrictions OR(Restrictions r1, Restrictions r2) {
        return new Restrictions(r1, r2, Operation.OR);
    }

    public static Restrictions startWith(String property,Object value) {
        return new Restrictions(property, String.valueOf(value), Operation.START_WITH);
    }
}
