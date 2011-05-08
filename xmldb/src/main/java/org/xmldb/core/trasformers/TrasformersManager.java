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

package org.xmldb.core.trasformers;

import java.util.HashMap;
import java.util.Map;
import org.xmldb.core.annotation.bean.PersistenceClass;
import org.xmldb.core.type.Sequence;

/**
 *
 * @author Giacomo Stefano Gabriele
 * @deprecated 
 */
public class TrasformersManager {

    protected static TrasformersManager instance;

    private static Map<Class,Trasformers> trasformers = new HashMap<Class, Trasformers>();

    private TrasformersManager(){
        trasformers.put(Sequence.class, new SequenceTrasformers());
    }


    public Trasformers getTrasformers(PersistenceClass persistenceClass){
        if(trasformers.containsKey(persistenceClass.getClazz())){
            return trasformers.get(persistenceClass.getClazz());
        }
        return new ObjectTrasformers(persistenceClass);
    }


    public synchronized  static TrasformersManager getInstance(){
        if(instance == null){
            instance = new TrasformersManager();
        }

        return instance;
    }
}
