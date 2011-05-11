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

package org.xmldb.core.annotation;

import java.util.HashMap;
import java.util.Map;
import org.xmldb.core.annotation.bean.PersistenceClass;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class PersistenceClassManager {

    private static final Map<Class,PersistenceClass> mapping = new HashMap<Class, PersistenceClass>();

    public static PersistenceClass get(Class clazz){
        if(!mapping.containsKey(clazz)){
            mapping.put(clazz, new PersistenceClass(clazz));
        }

        return mapping.get(clazz);
    }
}
