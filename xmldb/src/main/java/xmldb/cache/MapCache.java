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
package xmldb.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.apache.log4j.Logger;

import xmldb.criteria.Criteria;
import xmldb.exception.XmlDBException;
import xmldb.type.Sequence;


/**
 * 
 * @author Giacomo Stefano Gabriele
 *
 */
public class MapCache implements Cache {
    private static final Logger logger = Logger.getLogger(Cache.class);
    private static int CACHE_MAX_SIZE = 100;
    private Map<Class<?>, LinkedList<Pair>> cache;

    public MapCache() {
        cache = new HashMap<Class<?>, LinkedList<Pair>>(CACHE_MAX_SIZE);
    }

    public void setCacheSize(int cacheSize) {
        CACHE_MAX_SIZE = cacheSize;
    }

    @Override
    public boolean contains(Class<?> classe, Object key) {
        if (cache.containsKey(classe)) {

            int x = Collections.binarySearch(cache.get(classe), new Pair(key, null));

            return x >= 0;
            //return cache.get(classe).contains(new Pair(key, null));
        }
        return false;
    }

    @Override
    public Object get(Class<?> classe, Object key) {
        if (cache.containsKey(classe)) {
            int x = Collections.binarySearch(cache.get(classe), new Pair(key, null));
            if (x >= 0) {
                return cache.get(classe).get(x).getObj();
            }
        }
        throw new XmlDBException("La cache non contiene l'oggetto [" + classe + "] con id [" + key + "]");
    }

    @Override
    public void put(Class<?> classe, Object key, Object obj) {
        if (obj == null || isNull(key)) {
            return;
        }
        boolean instance = obj.getClass().isInstance(classe);
        if(!instance){
            instance = obj.getClass().getSuperclass().equals(classe);
        }
        if (!Criteria.class.equals(classe) && !instance && !Sequence.class.equals(classe)) {
            logger.error("La classe e l'oggetto sono di diversi tipi\n"+classe+" != "+obj.getClass());
            throw new XmlDBException("La classe e l'oggetto sono di diversi tipi");
        }
        if (!cache.containsKey(classe)) {
            cache.put(classe, new LinkedList<Pair>());
        } else {
            remove(classe, key);
        }

        Pair newPair = new Pair(key, obj);

        LinkedList<Pair> linkedList = cache.get(classe);

        int i = 0;
        for (; i < linkedList.size(); i++) {
            if (newPair.compareTo(linkedList.get(i)) < 0) {
                break;
            }
        }

        linkedList.add(i, newPair);
    }

    protected boolean isNull(Object id) {
        if (id == null) {
            return true;
        }
        if (id instanceof Integer) {
            return ((Integer) id) == 0;
        }
        if (id instanceof Double) {
            return ((Double) id) == 0;
        }
        if (id instanceof Float) {
            return ((Float) id) == 0;
        }
        if (id instanceof String) {
            return String.valueOf(id).trim().equals("") || String.valueOf(id).trim().equals("0") || String.valueOf(id).trim().equals("null");
        }
        return false;
    }

    public void remove(Class<?> classe, Object key) {
        if (cache.containsKey(classe)) {
            cache.get(classe).remove(new Pair(key, null));
        }
    }

    protected class Pair implements Comparable<Pair> {

        private Object key;
        private Object obj;

        public Pair(Object key, Object obj) {
            super();
            this.key = key;
            this.obj = obj;
        }

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Pair other = (Pair) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (key == null) {
                if (other.key != null) {
                    return false;
                }
            } else if (!key.equals(other.key)) {
                return false;
            }
            return true;
        }

        private MapCache getOuterType() {
            return MapCache.this;
        }

        @Override
        public String toString() {
            return "Pair [key=" + key + ", obj=" + obj + "]";
        }

        @Override
        public int compareTo(Pair o) {
            return hashCode() - o.hashCode();
        }
    }
}
