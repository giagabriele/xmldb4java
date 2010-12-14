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
package xmldb.session.impl;

import java.util.List;

import xmldb.Session;
import xmldb.cache.Cache;
import xmldb.cache.CacheManager;
import xmldb.configuration.AnnotationScanner;
import xmldb.criteria.Criteria;
import xmldb.transaction.Transaction;
import xmldb.util.AnnotationHelper;
import xmldb.util.ReflectionUtils;

/**
 * Implementazione della {@link Session} con {@link Transaction} e {@link Cache}
 * @see Transaction
 * @see Cache
 * @author Giacomo Stefano Gabriele
 * 
 */
@SuppressWarnings("unchecked")
public class CacheSessionTransaction extends TransactionSession {

    /**
     * La cache
     */
    private Cache cache = CacheManager.getCache();

    

    public CacheSessionTransaction() {
    }

    @Override
    public synchronized void delete(Class<? extends Object> classe, Object id) {
        super.delete(classe, id);

        cache.remove(classe, id);
    }

    @Override
    public synchronized <T> T merge(Object o, boolean lazy) {
        AnnotationScanner as = AnnotationHelper.get().get(o.getClass());

        if (lazy) {
            if (cache.contains(o.getClass(), ReflectionUtils.getValue(as.getId(), o))) {
                return (T) cache.get(o.getClass(), ReflectionUtils.getValue(as.getId(), o));
            }
        }
        T obj = (T) super.merge(o, lazy);
//		Aggiungo l'oggetto alla cache
        cache.put(o.getClass(), ReflectionUtils.getValue(as.getId(), o), o);
        return obj;

    }

//    @Override
//    public synchronized void persist(Object o) {
//        super.persist(o);
//
////		Aggiungo l'oggetto alla cache
////		cache.put(o.getClass(), ReflectionUtils.getIdObject(o), o);
//    }
    @Override
    public List find(Criteria criteria, boolean lazy) {
        super.controllaClasse(criteria.getClasse());
        if (lazy && cache.contains(Criteria.class, criteria.getXPathQuery())) {
            return (List) cache.get(Criteria.class, criteria.getXPathQuery());
        }
        List result = super.find(criteria, lazy);

        cache.put(Criteria.class, criteria.getXPathQuery(), result);

        return result;
    }

//    @Override
//    public List findAll(Class<? extends Object> classe, boolean lazy) {
//        return find(Criteria.createCriteria(classe), lazy);
//    }
    @Override
    public <T> T load(Class<? extends Object> classe, Object id, boolean lazy) {
        super.controllaClasse(classe);
        if (lazy) {
            //Lo prendo dalla cache se è lazy ed è abilitata la cache e se è in cache
            if (cache.contains(classe, id)) {
                return (T) cache.get(classe, id);
            }
        }

        T obj = (T) super.load(classe, id, lazy);

        cache.put(classe, id, obj);

        return obj;
    }
}
