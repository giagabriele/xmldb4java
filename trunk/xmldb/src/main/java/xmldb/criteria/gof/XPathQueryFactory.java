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
 * @author Giacomo Stefano Gabriele
 */
public class XPathQueryFactory {

    public static XPathQuery createLike(Class clazz,String property,Object value){
        return new Like(clazz, property, value);
    }
    
    public static XPathQuery createEq(Class clazz,String property,Object value){
        return new Eq(clazz, property, value);
    }

    public static XPathQuery createGt(Class clazz,String property,Object value){
        return new Gt(clazz, property, value);
    }

    public static XPathQuery createAnd(Class clazz,XPathQuery q1,XPathQuery q2){
        return new And(clazz, q1, q2);
    }


}
